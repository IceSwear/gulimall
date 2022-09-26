package com.kk.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.gulimall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spuÐÅÏ¢½éÉÜ
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);
}

