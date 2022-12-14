package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kk.gulimall.product.entity.ProductAttrValueEntity;
import com.kk.gulimall.product.service.ProductAttrValueService;
import com.kk.gulimall.product.vo.AttrResponseVo;
import com.kk.gulimall.product.vo.AttrVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kk.gulimall.product.entity.AttrEntity;
import com.kk.gulimall.product.service.AttrService;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;


/**
 * ÉÌÆ·ÊôÐÔ
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:48:45
 */
@RestController
@RequestMapping("product/attr")
@Slf4j
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


//    @GetMapping("/base/list/{catelogId}")
//    public R list(@RequestParam Map<String, Object> params, @PathVariable(value = "catelogId") Long catelogId) {
////        PageUtils page = attrService.queryPage(params);
//        log.info("/base/list/ param数据:{},id:{}", params, catelogId);
//        PageUtils page = attrService.queryPage(params, catelogId);
//        return R.ok().put("page", page);
//    }

    /**
     * 09、获取分类销售属性-get-/product/attr/sale/list/{catelogId}（同05）
     * @param params
     * @param catelogId
     * @param attrType
     * @return
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    public R salesList(@RequestParam Map<String, Object> params, @PathVariable(value = "catelogId") Long catelogId, @PathVariable(value = "attrType") String attrType) {
//        PageUtils page = attrService.queryPage(params);
        log.info("/{attrType}/list/param数据:{},id:{},类型为attrType:{}", params, catelogId, attrType);
        PageUtils page = attrService.queryPage(params, catelogId, attrType);
        return R.ok().put("page", page);
    }

    /**
     * 07、查询属性详情-get-/product/attr/info/{attrId}
     *
     * @param attrId
     * @return
     */
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
//        AttrEntity attr = attrService.getById(attrId);
        AttrResponseVo vo = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", vo);
    }


    /**
     * 保存属性【规格参数，销售属性】-post-/product/attr/save
     * @param attrVo
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attrVo) {
        attrService.saveAttr(attrVo);
        return R.ok();
    }

    /**
     * 08、修改属性-POST-/product/attr/update
     * @param attrVo
     * @return
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrVo) {
        log.info("08、修改属性-POST-/product/attr/update:{}",attrVo);
        attrService.updateAttr(attrVo);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }


    /**
     * 获取spu规格
     * https://easydoc.net/s/78237135/ZUqEdvA4/GhhJhkg7
     *获取spu规格-get-/product/attr/base/listforspu/{spuId}
     * @param spuId
     * @return
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R listForSpu(@PathVariable(value = "spuId") Long spuId) {
        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrListForSpu(spuId);
        return R.ok().put("data", entities);
    }


    /**
     * 修改商品规格
     * https://easydoc.net/s/78237135/ZUqEdvA4/GhnJ0L85
     *23、修改商品规格-post-/product/attr/update/{spuId}
     * @param spuId
     * @param entities
     * @return
     */
    @PostMapping("/update/{spuId}")
    public R update(@PathVariable(value = "spuId") Long spuId, @RequestBody List<ProductAttrValueEntity> entities) {
        log.info("spuId:{},entities: {}");
        productAttrValueService.updateSpuAttr(spuId, entities);
        return R.ok();
    }

}
