package com.kk.common.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 商品属性常量
 * @Author: Spike Wong
 * @Date: 2022/9/22
 */
@Slf4j
public class ProductConstant {

    public enum AttrEnum {
        ATTR_TYPE_BASE(1,"base"), ATTR_TYPE_SALE(0,"sale");

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        private int code;
        private String msg;

    }
}
