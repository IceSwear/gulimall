package com.kk.gulimall.product.service.impl;

import com.kk.gulimall.product.service.CategoryBrandRelationService;
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

import com.kk.gulimall.product.dao.CategoryDao;
import com.kk.gulimall.product.entity.CategoryEntity;
import com.kk.gulimall.product.service.CategoryService;

import javax.annotation.Resource;


@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Resource
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(new Query<CategoryEntity>().getPage(params), new QueryWrapper<CategoryEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //handle wrting sql    // select * select all
        List<CategoryEntity> entities = categoryDao.selectAll();
//        log.info("所有分类{}", entities);
        //,组装成父子的属性结构
        List<CategoryEntity> collect = entities.stream().filter(s -> s.getParentCid() == 0).map(s -> {
            s.setChildren(getChildrens(s, entities));
            return s;
        }).sorted((s1, s2) -> {
            return (s1.getSort() == null ? 0 : s1.getSort()) - (s2.getSort() == null ? 0 : s2.getSort());
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        //先检查 TODO 检查删除前是否被其他地方引用
        log.info("删除ids:{}", asList);
        categoryDao.logicallyDeletedByIds(asList);
    }


    /**
     * 找到Catelogid完整路径
     * 父/子/孙
     *
     * @param catelogId
     * @return
     */
    @Override
    public Long[] findCategoryPath(Long catelogId) {
        //new a  list
        List<Long> paths = new ArrayList<>();

        List<Long> parentPath = findParentPath(catelogId, paths);
        //反转，将集合反转，因为add进去有顺序
        Collections.reverse(parentPath);
        log.info("parentPath:{}", parentPath);
        //转成数组，且这个数组需要定义长度,要带入一个长度定义好的数组
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 递归方法
     *
     * @param catelogId
     * @param paths
     * @return
     */
    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        //收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        //条件，等于0就继续
        if (byId.getParentCid() != 0) {
            //将查出的对象继续迭代,直到条件不满足
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     */
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());

    }


    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(s -> {
            return s.getParentCid() == root.getCatId();
        }).map(s -> {
            s.setChildren(getChildrens(s, all));
            return s;
        }).sorted((s1, s2) -> {
            return (s1.getSort() == null ? 0 : s1.getSort()) - (s2  .getSort() == null ? 0 : s2.getSort());
        }).collect(Collectors.toList());
        return children;
    }
}