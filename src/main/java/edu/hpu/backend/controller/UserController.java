package edu.hpu.backend.controller;

import edu.hpu.backend.common.Result;
import edu.hpu.backend.dto.LoginDTO;     // 【关键】导入 LoginDTO
import edu.hpu.backend.dto.UserUpdateDTO;
import edu.hpu.backend.entity.User;
import edu.hpu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("后端启动成功！");
    }

    @GetMapping("/users")
    public Result<List<User>> getAllUsers() {
        return Result.success(userService.getAllUsers());
    }

    // 登录接口
    @PostMapping("/login")
    // 【修复】这里把 LoginRequest 改成 LoginDTO
    public Result<User> login(@RequestBody LoginDTO loginDTO) {
        // 使用 loginDTO.getEmail() 和 getPassword()
        User user = userService.login(loginDTO.getEmail(), loginDTO.getPassword());

        if (user != null) {
            user.setPassword(null); // 隐藏密码
            return Result.success("登录成功！", user);
        } else {
            return Result.error("邮箱或密码不正确！");
        }
    }

    // 更新用户信息接口
    @PostMapping("/update")
    public Result<User> update(@RequestBody UserUpdateDTO dto) {
        boolean success = userService.updateUserInfo(dto);
        if (success) {
            // 调用 Service 的公开方法获取最新用户信息
            User latestUser = userService.getUserById(dto.getId());
            latestUser.setPassword(null);
            return Result.success("保存成功", latestUser);
        }
        return Result.error("保存失败");
    }
}