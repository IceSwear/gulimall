package com.kk.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

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

    @GetMapping("/{attrType}/list/{catelogId}")
    public R salesList(@RequestParam Map<String, Object> params, @PathVariable(value = "catelogId") Long catelogId, @PathVariable(value = "attrType") String attrType) {
//        PageUtils page = attrService.queryPage(params);
        log.info("/{attrType}/list/param数据:{},id:{},类型为attrType:{}", params, catelogId,attrType);
        PageUtils page = attrService.queryPage(params, catelogId, attrType);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
//        AttrEntity attr = attrService.getById(attrId);
        AttrResponseVo vo = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", vo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attrVo) {
        attrService.saveAttr(attrVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrVo) {
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

}
