package com.kk.gulimall.product.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: brand vo
 * @Author: Spike Wong
 * @Date: 2022/9/26
 */
@Slf4j
@Data
public class BrandVo {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名字
     */
    private String brandName;
}
