package com.kk.gulimall.product.feign;

import com.kk.common.to.SkuReductionTo;
import com.kk.common.to.SpuBoundTo;
import com.kk.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/27
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody  SpuBoundTo spuBoundTo);


    @PostMapping("coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
