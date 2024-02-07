package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("staff")
@Data
public class Staff {
    @TableId(value ="id")
    private Integer id;
    private Integer number;
    private String position;
    private String name;
    private Integer year;
    private String sex;
   private BigDecimal salary;
}
