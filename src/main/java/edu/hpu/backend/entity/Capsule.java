package edu.hpu.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("capsule") // 对应数据库的 capsule 表
public class Capsule {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;    // 对应 user_id 字段
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openTime; // 开启时间

    private Integer status; // 0-未开启 1-已开启

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}