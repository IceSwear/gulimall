package com.kk.gulimall.member.feign;

import com.kk.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/27
 */

@FeignClient("gulimall-coupon")
public interface CouPonFeignService {

    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
