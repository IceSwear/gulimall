package com.kk.gulimall.product.service.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.kk.common.constant.ProductConstant;
import com.kk.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.kk.gulimall.product.dao.AttrGroupDao;
import com.kk.gulimall.product.dao.CategoryDao;
import com.kk.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.kk.gulimall.product.entity.AttrGroupEntity;
import com.kk.gulimall.product.entity.CategoryEntity;
import com.kk.gulimall.product.service.CategoryService;
import com.kk.gulimall.product.vo.AttrGroupRelationVo;
import com.kk.gulimall.product.vo.AttrResponseVo;
import com.kk.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.common.utils.PageUtils;
import com.kk.common.utils.Query;

import com.kk.gulimall.product.dao.AttrDao;
import com.kk.gulimall.product.entity.AttrEntity;
import com.kk.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Resource
    private AttrGroupDao attrGroupDao;

    @Resource
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    //    @Resource
//    private Attr
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), new QueryWrapper<AttrEntity>());

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attrVo) {
        //保存基本数据
        AttrEntity attr = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attr);
        this.save(attr);
        //保存关联关系
        //判断一下 类型
        if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrVo.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_SALE.getMsg().equalsIgnoreCase(attrType) ? ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            // wrapper.eq("attr_id", key).or().like("attr_name", key);
            //下面是更高级的写法？
            wrapper.and(s -> {
                s.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        //封装条件
        IPage<AttrEntity> iPage = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(iPage);
        //查完之后对vos进行设置
        List<AttrEntity> records = iPage.getRecords();
        List<AttrResponseVo> vos = records.stream().map(s -> {
            AttrResponseVo attrResponseVo = new AttrResponseVo();
            //先拷贝
            BeanUtils.copyProperties(s, attrResponseVo);
            //设置分类和分组的名字,判断是否为基本类型
            if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getMsg().equalsIgnoreCase(attrType)) {
                AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", s.getAttrId()));
                if (!Objects.isNull(attrId)) {
                    Long attrGroupId = attrId.getAttrGroupId();
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    if (!Objects.isNull(attrGroupEntity)) {
                        attrResponseVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }
            }

            CategoryEntity categoryEntity = categoryDao.selectById(s.getCatelogId());
            if (!Objects.isNull(categoryEntity)) {
                attrResponseVo.setCatelogName(categoryEntity.getName());
            }
            return attrResponseVo;
        }).collect(Collectors.toList());
        //重新设置在里面
        pageUtils.setList(vos);
        return pageUtils;
    }

    @Override
    public AttrResponseVo getAttrInfo(Long attrId) {
        AttrResponseVo vo = new AttrResponseVo();
        //查询当前属性信息
        AttrEntity attrEntity = this.getById(attrId);
        if (Objects.isNull(attrEntity)) {
            return new AttrResponseVo();
        }
        //非空的话
        BeanUtils.copyProperties(attrEntity, vo);

        //设置分组信息,先判断是否为基本类型
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if (!Objects.isNull(relationEntity)) {
                vo.setAttrGroupId(relationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (!Objects.isNull(attrGroupEntity)) {
                    vo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }

        //设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] categoryPath = categoryService.findCategoryPath(catelogId);
        vo.setCatelogPath(categoryPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (!Objects.isNull(categoryEntity)) {
            vo.setCatelogName(categoryEntity.getName());
        }
//        vo.setAttrGroupId();
//        vo.setCatelogPath();
        return vo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        //更新
        this.updateById(attrEntity);
        //先判断是否为基本属性
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            Integer attrId = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getAttrId()));
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            relationEntity.setAttrId(attrVo.getAttrId());
            if (attrId > 0) {
                //存在就更新
                attrAttrgroupRelationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getAttrId()));
            } else {
                //不存在就新增插入
                attrAttrgroupRelationDao.insert(relationEntity);
            }
        }

    }


    /**
     * 根据分组id查找关联的所有基本属性
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_group_id", attrgroupId);
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList(wrapper);
        List<Long> attrIds = entities.stream().map(s -> {
            return s.getAttrId();
        }).collect(Collectors.toList());

        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo... vos) {
//        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<>();
//        wrapper.eq("attr_id",1L).eq("attr_group_id",1L);
//        attrAttrgroupRelationDao.delete(wrapper);
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map(s -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(s, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        attrAttrgroupRelationDao.deleteBatchRelation(entities);
    }

}