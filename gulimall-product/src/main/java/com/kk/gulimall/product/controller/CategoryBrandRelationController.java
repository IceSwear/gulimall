package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.gulimall.product.entity.BrandEntity;
import com.kk.gulimall.product.vo.BrandVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kk.gulimall.product.entity.CategoryBrandRelationEntity;
import com.kk.gulimall.product.service.CategoryBrandRelationService;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;


/**
 * Æ·ÅÆ·ÖÀà¹ØÁª
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:48:45
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取品牌关联的分类-get-/product/categorybrandrelation/catelog/list
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/catelog/list", method = RequestMethod.GET)
    public R categoryList(@RequestParam(value = "brandId") Long brandId) {
          //PageUtils page = categoryBrandRelationService.queryPage(params);
        QueryWrapper<CategoryBrandRelationEntity> queryWrapper = new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId);
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(queryWrapper);
        return R.ok().put("data", data);
    }


    /**
     * p84 上传商品时显示分类关联品牌列表
     * 14、获取分类关联的品牌-get-/product/categorybrandrelation/brands/list
     * @param catId
     * @return
     */
    @GetMapping("/brands/list")
    public R relationBrandList(@RequestParam(value = "catId", required = true) Long catId) {
        //query by catelog id to get list of BrandEntity
        List<BrandEntity> list = categoryBrandRelationService.getBrandsByCatId(catId);
        //use stream to get Vo ,here as field is dfferent so we didn't use code
        List<BrandVo> collect = list.stream().map(s -> {
            BrandVo vo = new BrandVo();
            vo.setBrandId(s.getBrandId());
            vo.setBrandName(s.getName());
            //BeanUtils.copyProperties(s, vo);
            return vo;
        }).collect(Collectors.toList());
        //send back to front-end
        return R.ok().put("data", collect);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 新增品牌与分类关联关系
     *新增品牌与分类关联关系-post-product/categorybrandrelation/save
     * @param categoryBrandRelation
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
     // categoryBrandRelationService.save(categoryBrandRelation);
        categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
