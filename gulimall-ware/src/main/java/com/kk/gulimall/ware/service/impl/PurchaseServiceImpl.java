package com.kk.gulimall.ware.service.impl;

import com.kk.common.constant.WareConstant;
import com.kk.gulimall.ware.entity.PurchaseDetailEntity;
import com.kk.gulimall.ware.service.PurchaseDetailService;
import com.kk.gulimall.ware.service.WareSkuService;
import com.kk.gulimall.ware.vo.MergeVo;
import com.kk.gulimall.ware.vo.PurchaseDoneVo;
import com.kk.gulimall.ware.vo.PurchaseItemDoneVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.ware.dao.PurchaseDao;
import com.kk.gulimall.ware.entity.PurchaseEntity;
import com.kk.gulimall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("purchaseService")
@Slf4j
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params), new QueryWrapper<PurchaseEntity>());

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        log.info("params:{}", params);
        QueryWrapper<PurchaseEntity> qw = new QueryWrapper<>();

        String key = (String) params.get("key");
//        String status = (String) params.get("status");
//        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(key)) {
            qw.and(s -> {
                s.eq("purchase_id", key).or().like("sku_id", key);
            });
        }
//        if (!StringUtils.isEmpty(status)) {
//            qw.eq("status", status);
//        }
//        if (!StringUtils.isEmpty(wareId)) {
//            qw.eq("ware_id", wareId);
//        }

        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params), qw);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivePage(Map<String, Object> params) {
        log.info("unreceiveList:{}", params);
        QueryWrapper<PurchaseEntity> qw = new QueryWrapper<>();
        qw.eq("status", WareConstant.PurchaseStatusEnum.CREATED.getCode()).or().eq("status", 1);
        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params), qw);

        return new PageUtils(page);
    }


    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (Objects.isNull(purchaseId)) {
            //新建
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        //TODO 确认采购单0或者1 cai可以合并
        List<Long> items = mergeVo.getItems();
        Long finalPurchasedId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(s -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(s);
            purchaseDetailEntity.setPurchaseId(finalPurchasedId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);
        //更新时间
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);

    }

    @Override
    public void received(List<Long> purchaseIds) {
        //确认当前采购单新建 or 已分配
        List<PurchaseEntity> collect = purchaseIds.stream().map(id -> {
            PurchaseEntity byId = this.getById(id);
            return byId;
        }).filter(item -> {
            if (item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() || item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return true;
            }
            return false;
        }).map(item -> {
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
        //
        this.updateBatchById(collect);
        //
        collect.forEach(s -> {
            List<PurchaseDetailEntity> list = purchaseDetailService.listDetailByPurchaseId(s.getId());
            List<PurchaseDetailEntity> purchaseDetailEntities = list.stream().map(item -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(item.getId());
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(purchaseDetailEntities);
        });
    }


    @Transactional
    @Override
    public void done(PurchaseDoneVo purchaseDoneVo) {
        Long id = purchaseDoneVo.getId();

        //改变采购项状态
        Boolean flag = true;
        List<PurchaseDetailEntity> update = new ArrayList<>();
        List<PurchaseItemDoneVo> items = purchaseDoneVo.getItems();
        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity purchaseEntity = new PurchaseDetailEntity();
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()) {
                flag = false;
                purchaseEntity.setStatus(item.getStatus());
            } else {
                purchaseEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                //入库
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            }
            purchaseEntity.setId(item.getItemId());
            update.add(purchaseEntity);
        }
        purchaseDetailService.updateBatchById(update);
        //改变采购单状态
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISH.getCode() : WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);

        //将成功采购的进行入库


    }

}