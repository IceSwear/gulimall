package com.kk.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Ê×Ò³×¨Ìâ±í¡¾jdÊ×Ò³ÏÂÃæºÜ¶à×¨Ìâ£¬Ã¿¸ö×¨ÌâÁ´½ÓÐÂµÄÒ³Ãæ£¬Õ¹Ê¾×¨ÌâÉÌÆ·ÐÅÏ¢¡¿
 * 
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 14:45:08
 */
@Data
@TableName("sms_home_subject")
public class HomeSubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * ×¨ÌâÃû×Ö
	 */
	private String name;
	/**
	 * ×¨Ìâ±êÌâ
	 */
	private String title;
	/**
	 * ×¨Ìâ¸±±êÌâ
	 */
	private String subTitle;
	/**
	 * ÏÔÊ¾×´Ì¬
	 */
	private Integer status;
	/**
	 * ÏêÇéÁ¬½Ó
	 */
	private String url;
	/**
	 * ÅÅÐò
	 */
	private Integer sort;
	/**
	 * ×¨ÌâÍ¼Æ¬µØÖ·
	 */
	private String img;

}
