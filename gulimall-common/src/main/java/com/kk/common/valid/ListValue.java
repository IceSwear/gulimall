package com.kk.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
//将自己自定义的校验器 放进去
@Constraint(validatedBy = {ListValueConstraintValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {

    //消息
    String message() default "{com.kk.common.valid.ListValue.message}";


    //集群,分组校验的功能
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    //int的数组，默认无
    int[] values() default {};
}
