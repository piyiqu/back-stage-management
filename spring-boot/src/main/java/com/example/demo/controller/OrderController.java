package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Orderm;
import com.example.demo.mapper.OrderMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderMapper orderMapper;
    @PostMapping
    public Result<?> save(@RequestBody Orderm order)
    {

        orderMapper.insert(order);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Orderm order)
    {

        orderMapper.updateById(order);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id)
    {
        orderMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id)
    {
        return Result.success(orderMapper.selectById(id));
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search)

    {
        LambdaQueryWrapper<Orderm> wrapper=Wrappers.<Orderm>lambdaQuery();
        if (StrUtil.isNotBlank(search))
        {
            wrapper.like(Orderm::getName,search);
        }
        Page<Orderm> orderPage= orderMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(orderPage);
    }
}
