package com.kk.gulimall.ware.service.impl;

import com.kk.gulimall.ware.entity.PurchaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.ware.dao.PurchaseDetailDao;
import com.kk.gulimall.ware.entity.PurchaseDetailEntity;
import com.kk.gulimall.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;


@Service("purchaseDetailService")
@Slf4j
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetailEntity> page = this.page(new Query<PurchaseDetailEntity>().getPage(params), new QueryWrapper<PurchaseDetailEntity>());
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        log.info("params:{}", params);
        QueryWrapper<PurchaseDetailEntity> qw = new QueryWrapper<>();

        String key = (String) params.get("key");
        String status = (String) params.get("status");
        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(key)) {
            qw.and(s -> {
                s.eq("purchase_id", key).or().like("sku_id", key);
            });
        }
        if (!StringUtils.isEmpty(status)) {
            qw.eq("status", status);
        }
        if (!StringUtils.isEmpty(wareId)) {
            qw.eq("ware_id", wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(new Query<PurchaseDetailEntity>().getPage(params), qw);
        return new PageUtils(page);
    }

}