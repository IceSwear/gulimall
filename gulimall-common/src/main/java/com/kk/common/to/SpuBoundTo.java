package com.kk.common.to;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/27
 */
@Slf4j
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
