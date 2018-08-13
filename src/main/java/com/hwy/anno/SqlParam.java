package com.hwy.anno;

import java.lang.annotation.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 19:31
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlParam {

    int sort() default 1;

    String column() default "";

    ConditionTypeEnum[] type() default ConditionTypeEnum.EQUALS;

    boolean singleQuotes() default false;

    enum ConditionTypeEnum {
        EQUALS, LIKE, ASC, DESC
    }

}
