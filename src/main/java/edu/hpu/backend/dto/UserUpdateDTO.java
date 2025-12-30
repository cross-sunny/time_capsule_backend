package edu.hpu.backend.dto;

import lombok.Data;

@Data // 【关键】必须要有这个注解，不然 getId() 那些方法都会报错
public class UserUpdateDTO {
    private Long id;
    private String nickname;
    private String bio;
    private String avatar;
}