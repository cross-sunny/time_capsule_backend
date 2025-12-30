package edu.hpu.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CapsuleDTO {
    private String title;
    private String content;

    // 指定时间格式，防止前端传过来解析报错
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openTime;
}