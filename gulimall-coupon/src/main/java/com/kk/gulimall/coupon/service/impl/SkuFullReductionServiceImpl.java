package com.kk.gulimall.coupon.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.kk.common.to.MemberPrice;
import com.kk.common.to.SkuReductionTo;
import com.kk.gulimall.coupon.entity.MemberPriceEntity;
import com.kk.gulimall.coupon.entity.SkuLadderEntity;
import com.kk.gulimall.coupon.service.MemberPriceService;
import com.kk.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.coupon.dao.SkuFullReductionDao;
import com.kk.gulimall.coupon.entity.SkuFullReductionEntity;
import com.kk.gulimall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {


    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;
//
//    @Autowired
//    private SkuFullReductionService skuFullReductionService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
         //1,保存满减打折 会员价
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
        skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
        skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        if(skuReductionTo.getFullCount()>0){
            skuLadderService.save(skuLadderEntity);
        }

        //
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
        if(skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal("0"))==1){
            this.save(skuFullReductionEntity);
        }
        //3
        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        if(!CollUtil.isEmpty(memberPrice)){
            List<MemberPriceEntity> memberPriceEntities = memberPrice.stream().map(s -> {
                MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
//            BeanUtils.copyProperties(s,memberPriceEntity);
                memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
                memberPriceEntity.setMemberLevelId(s.getId());
                memberPriceEntity.setMemberLevelName(s.getName());
                memberPriceEntity.setMemberPrice(s.getPrice());
                memberPriceEntity.setAddOther(1);
                return memberPriceEntity;
            }).filter(item->{
               return item.getMemberPrice().compareTo(new BigDecimal("0"))==1;
            }).collect(Collectors.toList());
            memberPriceService.saveBatch(memberPriceEntities);
        }

    }

}