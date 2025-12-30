package edu.hpu.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hpu.backend.dto.RegisterDTO;
import edu.hpu.backend.dto.UserUpdateDTO;
import edu.hpu.backend.entity.User;
import edu.hpu.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper; // 它是 private 的，外部不能直接用

    // 获取所有用户
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    // 根据邮箱查找
    public User findByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return userMapper.selectOne(wrapper);
    }

    // 登录逻辑
    public User login(String email, String password) {
        User user = findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 注册逻辑
    public boolean register(RegisterDTO registerDTO) {
        if (findByEmail(registerDTO.getEmail()) != null) {
            throw new RuntimeException("该邮箱已被注册！");
        }
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setNickname(registerDTO.getNickname());
        return userMapper.insert(user) > 0;
    }

    // 更新用户信息
    // 更新用户信息
    public boolean updateUserInfo(UserUpdateDTO dto) {
        User user = userMapper.selectById(dto.getId());
        if (user == null) return false;

        // 1. 更新昵称
        if (dto.getNickname() != null && !dto.getNickname().isEmpty()) {
            user.setNickname(dto.getNickname());
        }

        // 2. 更新签名 (允许为空字符串，即清空签名)
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }

        // 3. 更新头像 (如果前端传了新路径)
        if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
            user.setAvatar(dto.getAvatar());
        }

        // 4. 执行 SQL 更新
        int rows = userMapper.updateById(user);
        return rows > 0;
    }

    // 【新增】公开查询 ID 的方法（解决 Controller 报错的问题）
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 重置密码
     */
    public boolean resetPassword(String email, String newPassword) {
        User user = findByEmail(email);
        if (user == null) {
            throw new RuntimeException("该邮箱未注册");
        }
        user.setPassword(newPassword);
        return userMapper.updateById(user) > 0;
    }
}