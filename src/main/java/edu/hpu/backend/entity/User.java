package edu.hpu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data // Lombok 自动生成 Getter/Setter
@TableName("user") // 对应数据库表名
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String avatar; // 新增
    private String bio;    // 新增
}