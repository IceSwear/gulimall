package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kk.gulimall.product.entity.SkuInfoEntity;
import com.kk.gulimall.product.service.SkuInfoService;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.R;


/**
 * skuÐÅÏ¢
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:48:45
 */
@RestController
@RequestMapping("product/skuinfo")
@Slf4j
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;


    /**
     * sku检索
     * https://easydoc.net/s/78237135/ZUqEdvA4/ucirLq1D
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        log.info("params:{}", params);
        PageUtils page = skuInfoService.queryPageByCondition(params);
        return R.ok().put("page", page);
    }
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = skuInfoService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     *
     * @param skuId
     * @return
     */
    @RequestMapping("/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId) {
        SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuInfoEntity skuInfo) {
        skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuInfoEntity skuInfo) {
        skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] skuIds) {
        skuInfoService.removeByIds(Arrays.asList(skuIds));

        return R.ok();
    }

}
