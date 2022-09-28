package com.kk.gulimall.product.dao;

import com.kk.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {


    /**
     * 查出所有分类
     *
     * @return
     */
    List<CategoryEntity> selectAll();


    /**
     * 逻辑删除catelogs by id
     *
     * @param catIds
     */
    void logicallyDeletedByIds(@Param(value = "catIds") List<Long> catIds);
}
