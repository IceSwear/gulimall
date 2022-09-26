package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kk.gulimall.product.entity.CategoryEntity;
import com.kk.gulimall.product.service.CategoryService;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;



/**
 * ÉÌÆ·Èý¼¶·ÖÀà
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:48:45
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * Query all levels of catelogs and show as tree style获取所有分类及子分类
     * @return
     */
    @RequestMapping("/list/tree")
    public R list(){
       List<CategoryEntity> entities =categoryService.listWithTree();
        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }


    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 删除 请求体，post才有请求体 必须发送 post ，自动将请求体转为对应对象
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
        //检查当前的删除菜单是否被别的地方引用
        categoryService.removeMenusByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
