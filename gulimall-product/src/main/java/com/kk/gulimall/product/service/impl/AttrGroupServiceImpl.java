package com.kk.gulimall.product.service.impl;

import com.kk.gulimall.product.entity.AttrEntity;
import com.kk.gulimall.product.service.AttrService;
import com.kk.gulimall.product.vo.AttrGroupWithAttrsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), new QueryWrapper<AttrGroupEntity>());

        return new PageUtils(page);
    }


    /**
     * 获取分类属性分组-get-/product/attrgroup/list/{catelogId}
     *
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
        //关键字如果不为空
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


    /**
     * 根据分类id查出所有的分组以及这些组里面的属性
     *17、获取分类下所有分组&关联属性-get-/product/attrgroup/{catelogId}/withattr
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        // 查询分组信息
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);
        List<AttrGroupEntity> list = this.list(queryWrapper);
        //查询书所有属性
        List<AttrGroupWithAttrsVo> collect = list.stream().map(s -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(s, vo);
            List<AttrEntity> relationAttr = attrService.getRelationAttr(vo.getAttrGroupId());
            //将集合也放进去
            vo.setAttrs(relationAttr);
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }
}