package com.kk.gulimall.product.service.impl;

import com.kk.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.kk.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.kk.gulimall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(new Query<AttrAttrgroupRelationEntity>().getPage(params), new QueryWrapper<AttrAttrgroupRelationEntity>());

        return new PageUtils(page);
    }


    /**
     * 批量保存 P82
     *
     * @param vos
     */
    @Override
    public void saveBatch(List<AttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> collect = vos.stream().map(s -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(s, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }

}