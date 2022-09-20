package com.kk.common.exception;

/**
 * @Description: 枚举状态码
 * @Author: Spike Wong
 * @Date: 2022/9/20
 */
public enum BizCodeEnume {

    UNKNOWN_EXCEPTION(10000,"系统"),
    VALID_EXCEPTION(10001,"参数格式校验失败");

     private  int code;
     private String msg;
    BizCodeEnume(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}
