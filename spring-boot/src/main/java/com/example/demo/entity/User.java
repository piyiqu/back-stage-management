package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    @TableId(value ="id")
    private Integer id;
    private String nickname;
    private String username;
    private String password;
    private Integer age;
    private String sex;
    private String address;
    private String role;
}
