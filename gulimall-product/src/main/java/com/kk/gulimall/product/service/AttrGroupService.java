package com.kk.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.common.utils.PageUtils;
import com.kk.gulimall.product.entity.AttrGroupEntity;
import com.kk.gulimall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * ÊôÐÔ·Ö×é
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCatelogId(Map<String, Object> params, Long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}

