package edu.hpu.backend.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hpu.backend.entity.Capsule;
import edu.hpu.backend.entity.User;
import edu.hpu.backend.mapper.CapsuleMapper;
import edu.hpu.backend.mapper.UserMapper;
import edu.hpu.backend.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CapsuleTask {

    @Autowired
    private CapsuleMapper capsuleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 定时扫描任务
     * cron表达式: "0 * * * * ?" 表示每分钟的第0秒执行一次
     */
    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class) // 保证数据库操作的原子性
    public void checkCapsules() {
        // 1. 查询所有符合条件的胶囊：未开启(0) 且 到达开启时间
        LambdaQueryWrapper<Capsule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Capsule::getStatus, 0); // 状态为0
        wrapper.le(Capsule::getOpenTime, LocalDateTime.now()); // 时间 <= 现在

        List<Capsule> capsuleList = capsuleMapper.selectList(wrapper);

        if (capsuleList.isEmpty()) {
            return; // 没有要处理的胶囊，直接结束
        }

        System.out.println("扫描到 " + capsuleList.size() + " 个到期的胶囊，开始处理...");

        // 2. 遍历处理每一个胶囊
        for (Capsule capsule : capsuleList) {
            try {
                // A. 修改状态为 1 (已开启)
                capsule.setStatus(1);
                capsuleMapper.updateById(capsule);

                // B. 查出用户邮箱
                User user = userMapper.selectById(capsule.getUserId());
                if (user != null) {
                    // C. 发送邮件
                    String subject = "【时光胶囊】您有一封来自过去的信已开启 📬";
                    String content = "亲爱的 " + user.getNickname() + "：\n\n" +
                            "您在 " + capsule.getCreateTime() + " 埋下的时光胶囊终于到了开启的时刻！\n\n" +
                            "----------------------------------\n" +
                            "【标题】： " + capsule.getTitle() + "\n" +
                            "【内容】：\n" + capsule.getContent() + "\n" +
                            "----------------------------------\n\n" +
                            "愿您不负韶华，未来可期。\n" +
                            "—— 时光胶囊项目组";

                    emailUtil.sendSimpleMail(user.getEmail(), subject, content);
                    System.out.println("胶囊(ID:" + capsule.getId() + ") 已开启并发送邮件给: " + user.getEmail());
                }
            } catch (Exception e) {
                System.err.println("处理胶囊(ID:" + capsule.getId() + ") 失败: " + e.getMessage());
            }
        }
    }
}