package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kk.gulimall.product.entity.AttrEntity;
import com.kk.gulimall.product.service.AttrService;
import com.kk.gulimall.product.service.CategoryService;
import com.kk.gulimall.product.vo.AttrGroupRelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kk.gulimall.product.entity.AttrGroupEntity;
import com.kk.gulimall.product.service.AttrGroupService;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;


/**
 * ÊôÐÔ·Ö×é
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:48:45
 */
@RestController
@Slf4j
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取分类属性接口
     * @param params
     * @param catelogId
     * @return
     */
    @GetMapping("/list/{catelogId}")
    public R listByCatelogId(@RequestParam Map<String, Object> params, @PathVariable(value = "catelogId") Long catelogId) {
        log.info("查询参数param:{}", params);
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPageByCatelogId(params, catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 获取属性分组详情
     * @param attrGroupId
     * @return
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        //query to get attrgourp object 先按照id找到对象
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        //get catelog id
        Long catelogId = attrGroup.getCatelogId();
        //then find Category Path by id
        Long path[] = categoryService.findCategoryPath(catelogId);

        //put the catlogpth back to attrgroup object
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }


    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data", entities);
    }


    /**
     * 删除关联关系
     *
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R relationDelete(@RequestBody AttrGroupRelationVo[] vos) {
        attrService.deleteRelation(vos);
        return R.ok();
    }
}
