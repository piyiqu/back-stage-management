package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Goods;
import com.example.demo.entity.Staff;
import com.example.demo.mapper.GoodsMapper;
import com.example.demo.mapper.StaffMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Resource
    StaffMapper staffMapper;
    @PostMapping
    public Result<?> save(@RequestBody Staff staff)
    {

        staffMapper.insert(staff);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Staff staff)
    {

        staffMapper.updateById(staff);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id)
    {
        staffMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id)
    {
        return Result.success(staffMapper.selectById(id));
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search)

    {
        LambdaQueryWrapper<Staff> wrapper=Wrappers.<Staff>lambdaQuery();
        if (StrUtil.isNotBlank(search))
        {
            wrapper.like(Staff::getName,search);
        }
        Page<Staff> staffPage= staffMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(staffPage);
    }
}
