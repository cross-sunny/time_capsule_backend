package edu.hpu.backend.service;

import edu.hpu.backend.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    @Autowired
    private EmailUtil emailUtil;

    // 内存存储验证码
    public static final Map<String, String> CODE_STORE = new ConcurrentHashMap<>();

    public void sendCode(String email) {
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        CODE_STORE.put(email, code);
        emailUtil.sendSimpleMail(email, "【时光胶囊】注册验证码", "您的验证码是：" + code + "，有效期5分钟。");
        System.out.println("验证码已发送至: " + email + ", code: " + code); // 控制台打印，方便测试
    }

    /**
     * 校验验证码 (新增方法)
     */
    public boolean verifyCode(String email, String inputCode) {
        String correctCode = CODE_STORE.get(email);
        // 验证码存在 且 输入正确
        if (correctCode != null && correctCode.equals(inputCode)) {
            CODE_STORE.remove(email); // 验证成功后立即移除，防止重复使用
            return true;
        }
        return false;
    }
}