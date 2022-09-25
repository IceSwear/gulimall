package com.kk.gulimall.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.product.dao.AttrGroupDao;
import com.kk.gulimall.product.entity.AttrGroupEntity;
import com.kk.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
@Slf4j
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), new QueryWrapper<AttrGroupEntity>());

        return new PageUtils(page);
    }


    /**
     * 获取分类属性接口 查询
     * @param params
     * @param catelogId
     * @return
     */
    @Override
    public PageUtils queryPageByCatelogId(Map<String, Object> params, Long catelogId) {
        //初始化一个mp的ipage
        IPage<AttrGroupEntity> page = null;
        //initialize a  new object
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        //if catelogid doesn't equal to 0, it means that thre is an appointed condition
        if (catelogId != 0) {
            wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);
        }
        //get key from the map 从map里获取key
        String key = (String) params.get("key");
        //judge that if key is empty or not. true=set condition false =not to set empty
        if (!StringUtils.isEmpty(key)) {
            log.info("关键字key不为空:{}", key);
            //here is a good example to seal a method like this
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }
        //这是封装好的信息，就是讲map里的分页信息直接封装
        page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}