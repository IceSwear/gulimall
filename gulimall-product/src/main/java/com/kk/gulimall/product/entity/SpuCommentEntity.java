package com.kk.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
@Data
@TableName("pms_spu_comment")
public class SpuCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;

	private Long skuId;

	private Long spuId;

	private String spuName;

	private String memberNickName;

	private Integer star;

	private String memberIp;

	private Date createTime;

	private Integer showStatus;

	private String spuAttributes;

	private Integer likesCount;

	private Integer replyCount;

	private String resources;

	private String content;

	private String memberIcon;

	private Integer commentType;

}
