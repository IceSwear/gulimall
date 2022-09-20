package com.kk.gulimall.product.exception;

import com.kk.common.exception.BizCodeEnume;
import com.kk.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: api 统一异常处理
 * @Author: Spike Wong
 * @Date: 2022/9/20
 */
@Slf4j
//@ResponseBody
//@ControllerAdvice(basePackages = "com.kk.gulimall.product.controller")//有这个 其他2个就都不要了
@RestControllerAdvice(basePackages = "com.kk.gulimall.product.controller")
public class GulimallExceptionApiAdvice {


    //要以json形式写出去，所以需要用ReponseBody注解
//    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.info("异常内容：{},异常类型{}", e.getMessage(), e.getClass());
        //new a map
        Map<String, String> map = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();

        bindingResult.getFieldErrors().forEach(s -> {
            String defaultMessage = s.getDefaultMessage();
            String field = s.getField();
            map.put(field, defaultMessage);
        });
        return R.error(BizCodeEnume.VALID_EXCEPTION.getCode(), BizCodeEnume.VALID_EXCEPTION.getMsg()).put("data", map);
    }


    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        return R.error();
    }
}
