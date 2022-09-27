package com.kk.gulimall.ware.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.ware.dao.WareSkuDao;
import com.kk.gulimall.ware.entity.WareSkuEntity;
import com.kk.gulimall.ware.service.WareSkuService;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
@Slf4j
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params), new QueryWrapper<WareSkuEntity>());

        return new PageUtils(page);
    }


    /**
     * 分页条件查询
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        log.info("params:{}", params);
        QueryWrapper<WareSkuEntity> qw = new QueryWrapper<>();
        String wareId = (String) params.get("wareId");
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)) {
            qw.eq("sku_id", skuId);
        }
        if (!StringUtils.isEmpty(wareId)) {
            qw.eq("ware_id", wareId);
        }
        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params), qw);
        return null;
    }

}