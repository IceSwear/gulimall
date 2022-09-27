package com.kk.gulimall.ware.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.ware.dao.WareInfoDao;
import com.kk.gulimall.ware.entity.WareInfoEntity;
import com.kk.gulimall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareInfoEntity> page = this.page(new Query<WareInfoEntity>().getPage(params), new QueryWrapper<WareInfoEntity>());

        return new PageUtils(page);
    }


    /**
     * query by condition
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<WareInfoEntity> qw = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            qw.and(s -> {
                s.eq("id", key).or().like("name", key).or().like("address", key).or().like("areacode", key);
            });
        }

        IPage<WareInfoEntity> page = this.page(new Query<WareInfoEntity>().getPage(params), qw);

        return new PageUtils(page);
    }

}