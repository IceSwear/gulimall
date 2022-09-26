package com.kk.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spuÐÅÏ¢½éÉÜ
 * 
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
@Data
@TableName("pms_spu_info_desc")
public class SpuInfoDescEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ÉÌÆ·id
	 */
	@TableId(type = IdType.INPUT)
	private Long spuId;
	/**
	 * ÉÌÆ·½éÉÜ
	 */
	private String decript;

}
