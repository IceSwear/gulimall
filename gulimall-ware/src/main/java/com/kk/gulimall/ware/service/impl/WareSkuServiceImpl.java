package com.kk.gulimall.ware.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.kk.common.utils.R;
import com.kk.gulimall.ware.feign.ProductFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


    @Autowired
    private WareSkuDao wareSkuDao;

    @Autowired
    private ProductFeignService productFeignService;

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
        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //如果还没有这个保存记录
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (CollUtil.isEmpty(wareSkuEntities)) {
            //empty - add
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            //TODO 查询远程sku的名字
            try {
                R info = productFeignService.info(skuId);
                //是状态码0就取出来
                if (info.getCode() == 0) {
                    Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {
                System.out.println("远程出现异常");
            }
            wareSkuDao.insert(wareSkuEntity);
        }
        wareSkuDao.updateStock(skuId, wareId, skuNum);
    }

}