package com.kk.gulimall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.kk.gulimall.product.entity.AttrEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/26
 */
@Slf4j
@Data
public class AttrGroupWithAttrsVo {
    /**
     * ·Ö×éid
     */
    private Long attrGroupId;
    /**
     * ×éÃû
     */
    private String attrGroupName;
    /**
     * ÅÅÐò
     */
    private Integer sort;
    /**
     * ÃèÊö
     */
    private String descript;
    /**
     * ×éÍ¼±ê
     */
    private String icon;
    /**
     * ËùÊô·ÖÀàid
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
