package com.kk.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.gulimall.product.entity.SpuInfoDescEntity;
import com.kk.gulimall.product.entity.SpuInfoEntity;
import com.kk.gulimall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spuÐÅÏ¢
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

     void upSpu(Long spuId);

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuSaveVo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

