package edu.hpu.backend.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String email;    // 邮箱
    private String password; // 密码
    private String code;     // 验证码
    private String nickname; // 昵称
}