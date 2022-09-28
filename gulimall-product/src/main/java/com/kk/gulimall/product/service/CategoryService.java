package com.kk.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * ÉÌÆ·Èý¼¶·ÖÀà
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * Query all levels of catelogs and show as tree style获取所有分类及子分类
     * @return
     */
    List<CategoryEntity> listWithTree();

    /**
     * 删除 请求体，post才有请求体 必须发送 post ，自动将请求体转为对应对象
     * @param catIds
     */
    void removeMenusByIds(List<Long> catIds);


    /**
     * 找到categoryid 的完整路径
     * [父/子/孙]
     * @param catelogId
     * @return
     */
    Long[] findCategoryPath(Long catelogId);

    void updateCascade(CategoryEntity category);

}

