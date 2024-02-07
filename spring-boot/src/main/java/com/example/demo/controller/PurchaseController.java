package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Orderm;
import com.example.demo.entity.Purchase;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.PurchaseMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Resource
    PurchaseMapper purchaseMapper;
    @PostMapping
    public Result<?> save(@RequestBody Purchase purchase)
    {

        purchaseMapper.insert(purchase);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Purchase purchase)
    {

        purchaseMapper.updateById(purchase);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id)
    {
        purchaseMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id)
    {
        return Result.success(purchaseMapper.selectById(id));
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search)

    {
        LambdaQueryWrapper<Purchase> wrapper=Wrappers.<Purchase>lambdaQuery();
        if (StrUtil.isNotBlank(search))
        {
            wrapper.like(Purchase::getName,search);
        }
        Page<Purchase> pagePage= purchaseMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(pagePage);
    }
}
