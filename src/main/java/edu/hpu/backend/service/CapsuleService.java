package edu.hpu.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hpu.backend.dto.CapsuleDTO;
import edu.hpu.backend.entity.Capsule;
import edu.hpu.backend.mapper.CapsuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CapsuleService {

    @Autowired
    private CapsuleMapper capsuleMapper;

    // 埋胶囊
    public void create(Long userId, CapsuleDTO dto) {
        Capsule capsule = new Capsule();
        capsule.setUserId(userId);
        capsule.setTitle(dto.getTitle());
        capsule.setContent(dto.getContent());
        capsule.setOpenTime(dto.getOpenTime());
        capsule.setCreateTime(LocalDateTime.now());
        capsule.setStatus(0); // 0-未开启

        capsuleMapper.insert(capsule);
    }

    // 获取某人的所有胶囊
    public List<Capsule> getMyCapsules(Long userId) {
        LambdaQueryWrapper<Capsule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Capsule::getUserId, userId);
        wrapper.orderByDesc(Capsule::getCreateTime); // 按创建时间倒序
        return capsuleMapper.selectList(wrapper);
    }
}