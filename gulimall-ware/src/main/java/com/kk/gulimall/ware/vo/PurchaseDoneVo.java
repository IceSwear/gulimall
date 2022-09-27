package com.kk.gulimall.ware.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/27
 */
@Slf4j
@Data
public class PurchaseDoneVo {


    @NotNull
    private Long id;


    private List<PurchaseItemDoneVo> items;
}
