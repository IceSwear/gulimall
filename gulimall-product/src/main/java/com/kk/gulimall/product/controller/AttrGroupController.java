package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kk.gulimall.product.entity.AttrEntity;
import com.kk.gulimall.product.service.AttrAttrgroupRelationService;
import com.kk.gulimall.product.service.AttrService;
import com.kk.gulimall.product.service.CategoryService;
import com.kk.gulimall.product.vo.AttrGroupRelationVo;
import com.kk.gulimall.product.vo.AttrGroupWithAttrsVo;
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


    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;


    /**
     * 11、添加属性与分组关联关系-post-/product/attrgroup/attr/relation
     *
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        attrAttrgroupRelationService.saveBatch(vos);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取分类属性分组-get-/product/attrgroup/list/{catelogId}
     * https://easydoc.net/s/78237135/ZUqEdvA4/OXTgKobR
     *
     * @param params
     * @param catelogId
     * @return {
     * "msg": "success",
     * "code": 0,
     * "page": {
     * <p>
     * }
     */
    @GetMapping("/list/{catelogId}")
    public R listByCatelogId(@RequestParam Map<String, Object> params, @PathVariable(value = "catelogId") Long catelogId) {
        log.info("获取分类属性分组param:{}", params);
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPageByCatelogId(params, catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 获取属性分组详情-get-/product/attrgroup/info/{attrGroupId}
     *
     * @param attrGroupId
     * @return
     */
    @GetMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        log.info("获取属性分组详情-attrGroupId:{}", attrGroupId);
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


    /**
     * 获取属性分组的关联的所有属性-get-/product/attrgroup/{attrgroupId}/attr/relation
     *
     * @param attrgroupId
     * @return
     */

    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data", entities);
    }


    /**
     * 13、获取属性分组没有关联的其他属性-get-/product/attrgroup/{attrgroupId}/noattr/relation
     *
     * @param attrgroupId
     * @param params
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R noattrRelation(@PathVariable("attrgroupId") Long attrgroupId, @RequestParam Map<String, Object> params) {
        log.info("/{attrgroupId}/noattr");
        PageUtils page = attrService.getNoRelationAttr(params, attrgroupId);
        return R.ok().put("page", page);
    }


    /**
     * 12、删除属性与分组的关联关系-post-/product/attrgroup/attr/relation/delete
     *
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R relationDelete(@RequestBody AttrGroupRelationVo[] vos) {
        log.info("12、删除属性与分组的关联关系vos:{}}", vos);
        attrService.deleteRelation(vos);
        return R.ok();
    }


    /**
     * 通过catelogId 获取attr分组以及所有属性
     * 17、获取分类下所有分组&关联属性-get-/product/attrgroup/{catelogId}/withattr
     * https://easydoc.net/s/78237135/ZUqEdvA4/6JM6txHf
     *
     * @param catelogId
     * @return
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable(value = "catelogId") Long catelogId) {
        //查出当前分类下的所有属性分组
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        //查出每个属性分组的所有属性
        return R.ok().put("data", vos);
    }
}
