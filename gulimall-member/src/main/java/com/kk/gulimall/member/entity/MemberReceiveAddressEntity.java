package com.kk.gulimall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * »áÔ±ÊÕ»õµØÖ·
 * 
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 14:58:42
 */
@Data
@TableName("ums_member_receive_address")
public class MemberReceiveAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member_id
	 */
	private Long memberId;
	/**
	 * ÊÕ»õÈËÐÕÃû
	 */
	private String name;
	/**
	 * µç»°
	 */
	private String phone;
	/**
	 * ÓÊÕþ±àÂë
	 */
	private String postCode;
	/**
	 * Ê¡·Ý/Ö±Ï½ÊÐ
	 */
	private String province;
	/**
	 * ³ÇÊÐ
	 */
	private String city;
	/**
	 * Çø
	 */
	private String region;
	/**
	 * ÏêÏ¸µØÖ·(½ÖµÀ)
	 */
	private String detailAddress;
	/**
	 * Ê¡ÊÐÇø´úÂë
	 */
	private String areacode;
	/**
	 * ÊÇ·ñÄ¬ÈÏ
	 */
	private Integer defaultStatus;

}
