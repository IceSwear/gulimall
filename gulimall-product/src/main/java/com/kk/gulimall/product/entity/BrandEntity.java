package com.kk.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.kk.common.valid.AddGroup;
import com.kk.common.valid.ListValue;
import com.kk.common.valid.UpdateGroup;
import com.kk.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 *
 * @author KK Wong
 * @email huangyk@mail.sustech.edu.cn
 * @date 2022-09-15 13:10:15
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Null(message = "新增不能指定id",groups = {AddGroup.class})
    @NotNull(message = "修改必须指定品牌id",groups = {UpdateGroup.class})
    @TableId
    private Long brandId;

    @NotBlank(message = "品牌名必须提交",groups ={AddGroup.class,UpdateGroup.class} )
    private String name;

    @NotBlank(groups = {AddGroup.class})
    @URL(groups = {AddGroup.class,UpdateGroup.class})
    private String logo;

    private String descript;

    @NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
    @ListValue(values={0,1},groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;

    @NotEmpty(groups ={AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母",groups ={AddGroup.class,UpdateGroup.class})
    private String firstLetter;

    @NotNull(groups ={AddGroup.class})
    @Min(value = 0, message = "排序必须大于等于0",groups ={AddGroup.class,UpdateGroup.class})
    private Integer sort;

}
