package com.example.demo.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("purchase")
@Data
public class Purchase {
    @TableId(value ="id")
    private Integer id;
    private Integer userid;
    private String name;
    private BigDecimal price;
    private Integer number;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date buydate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startdate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date enddate;


}
