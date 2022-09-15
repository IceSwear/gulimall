package com.kk.gulimall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ÍË¿îÐÅÏ¢
 * 
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 15:17:34
 */
@Data
@TableName("oms_refund_info")
public class RefundInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * ÍË¿îµÄ¶©µ¥
	 */
	private Long orderReturnId;
	/**
	 * ÍË¿î½ð¶î
	 */
	private BigDecimal refund;
	/**
	 * ÍË¿î½»Ò×Á÷Ë®ºÅ
	 */
	private String refundSn;
	/**
	 * ÍË¿î×´Ì¬
	 */
	private Integer refundStatus;
	/**
	 * ÍË¿îÇþµÀ[1-Ö§¸¶±¦£¬2-Î¢ÐÅ£¬3-ÒøÁª£¬4-»ã¿î]
	 */
	private Integer refundChannel;
	/**
	 * 
	 */
	private String refundContent;

}
