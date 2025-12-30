package edu.hpu.backend.controller;

import edu.hpu.backend.dto.ResetPasswordDTO;
import edu.hpu.backend.dto.RegisterDTO;
import edu.hpu.backend.service.UserService;
import edu.hpu.backend.common.Result; // 注意这里 import 变了
import edu.hpu.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private EmailService emailService;

    @Autowired // 注入 UserService
    private UserService userService;

    // 发送验证码接口
    // 前端请求：POST /api/auth/send-code?email=xxx@qq.com
    @PostMapping("/send-code")
    public Result<String> sendCode(@RequestParam String email) {
        try {
            emailService.sendCode(email);
            return Result.success("验证码发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("发送失败，请检查邮箱地址");
        }
    }

    /**
     * 注册接口 (新增)
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO) {
        // 1. 校验验证码
        boolean isCodeValid = emailService.verifyCode(registerDTO.getEmail(), registerDTO.getCode());
        if (!isCodeValid) {
            return Result.error("验证码错误或已失效");
        }

        // 2. 执行注册
        try {
            userService.register(registerDTO);
            return Result.success("注册成功！请登录");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage()); // 比如“邮箱已被注册”
        }
    }

    /**
     * 重置密码接口 (新增)
     */
    @PostMapping("/reset-password")
    public Result<String> resetPassword(@RequestBody ResetPasswordDTO dto) {
        // 1. 校验验证码
        boolean isCodeValid = emailService.verifyCode(dto.getEmail(), dto.getCode());
        if (!isCodeValid) {
            return Result.error("验证码错误或已失效");
        }

        // 2. 重置密码
        try {
            userService.resetPassword(dto.getEmail(), dto.getNewPassword());
            return Result.success("密码重置成功，请登录");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}