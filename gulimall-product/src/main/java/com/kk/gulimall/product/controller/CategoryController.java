package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kk.gulimall.product.entity.CategoryEntity;
import com.kk.gulimall.product.service.CategoryService;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;


/**
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:48:45
 */
@RestController
@RequestMapping("product/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * Query all levels of catelogs and show as tree style获取所有分类及子分类
     * 获取所有分类及子分类 P46
     * https://easydoc.net/s/78237135/ZUqEdvA4/HqQGp9TI
     *
     * @return {
     * "code": 0,
     * "msg": "success",
     * "data": [{
     * "catId": 1,
     * "name": "图书、音像、电子书刊",
     * "parentCid": 0,
     * "catLevel": 1,
     * "showStatus": 1,
     * "sort": 0,
     * "icon": null,
     * "productUnit": null,
     * "productCount": 0,
     * "children": []
     * }]
     * }
     */
    @RequestMapping("/list/tree")
    public R list() {
        List<CategoryEntity> entities = categoryService.listWithTree();
        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }


    /**
     * P56,修改分类父子关系以及顺序-/product/category/update/sort (三级分类中的场景，拖拽变换关系及顺序)
     * https://easydoc.net/s/78237135/ZUqEdvA4/VcZDIGmW
     *
     * @param category
     * @return {
     * "msg": "success",
     * "code": 0
     * }
     */
    @PostMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category) {
        log.info("修改分类父子关系以及顺序-category:{}", category);
        categoryService.updateBatchById(Arrays.asList(category));
        //TODO 如果这里手写sql，感觉是一个for循环 + 判空 + 更新 ,下面是入参
        // "catId": 0, //菜单id
        //    "catLevel": 0, //菜单层级
        //    "parentCid": 0, //父菜单id
        //    "sort": 0 //排序
//        categoryService.updateBatchByIds(Arrays.asList(category));
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 删除 请求体，post才有请求体 必须发送 post ，自动将请求体转为对应对象
     *
     * @param catIds
     * @return
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
        //judge if  current catelog has been used somewhere
        //判断当前的删除菜单是否被别的地方引用
        categoryService.removeMenusByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
