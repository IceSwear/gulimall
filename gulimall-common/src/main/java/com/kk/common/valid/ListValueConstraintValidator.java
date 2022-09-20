package com.kk.common.valid;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Description:
 * @Author: Spike Wong
 * @Date: 2022/9/20
 */
@Slf4j
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {

    private Set<Integer> set =new HashSet<>();
    /**
     * 初始化方法
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
        int[] values = constraintAnnotation.values();
        if (!Objects.isNull(values)){
            for (int value : values) {
                set.add(value);
            }
        }
    }

    /**
     * 判断是否校验成功 需要校验得知
     * @param value 需要校验得知
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        //这里就是进行校验的
        return set.contains(value);
    }
}
