package com.kk.gulimall.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.product.dao.ProductAttrValueDao;
import com.kk.gulimall.product.entity.ProductAttrValueEntity;
import com.kk.gulimall.product.service.ProductAttrValueService;
import org.springframework.transaction.annotation.Transactional;


@Service("productAttrValueService")
@Slf4j
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(new Query<ProductAttrValueEntity>().getPage(params), new QueryWrapper<ProductAttrValueEntity>());

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttr(List<ProductAttrValueEntity> collect) {
        this.saveBatch(collect);
    }


    /**
     * 获取spu规格
     * https://easydoc.net/s/78237135/ZUqEdvA4/GhhJhkg7
     * @param spuId
     * @return
     */
    @Override
    public List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId) {
        log.info("spuId:{}", spuId);
        QueryWrapper<ProductAttrValueEntity> qw = new QueryWrapper<>();
        qw.eq("spu_id", spuId);
        return this.baseMapper.selectList(qw);


    }


    /**
     * 修改商品规格
     * @param spuId
     * @param entities
     */
    @Transactional
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities) {
        //1.删除
        QueryWrapper<ProductAttrValueEntity> removeCondition = new QueryWrapper<ProductAttrValueEntity>();
        removeCondition.eq("spu_id", spuId);
        this.baseMapper.delete(removeCondition);

        //插入
        List<ProductAttrValueEntity> valueEntities = entities.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());
        this.saveBatch(valueEntities);
    }

}