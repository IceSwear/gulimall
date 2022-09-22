package com.kk.gulimall.product.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/22
 */
@Slf4j
@Data
public class AttrResponseVo extends AttrVo {
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}
