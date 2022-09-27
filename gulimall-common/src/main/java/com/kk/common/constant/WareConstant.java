package com.kk.common.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/27
 */
@Slf4j
public class WareConstant {

    public enum PurchaseStatusEnum {
        CREATED(0, "新建"), ASSIGNED(1, "已分配"), RECEIVE(2, "已领取"), FINISH(3, "已完成"), HASERROR(4, "异常");

        PurchaseStatusEnum(int code, String msg) {
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


    public enum PurchaseDetailStatusEnum {
        CREATED(0, "新建"), ASSIGNED(1, "已分配"), BUYING(2, "正在采购"), FINISH(3, "已完成"), HASERROR(4, "采购失败");

        PurchaseDetailStatusEnum(int code, String msg) {
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
