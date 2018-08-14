package com.hwy.anno;

import java.lang.annotation.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: sql参数定义注解
 * @date 2018/8/9 19:31
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlParam {

    /**
     * 排序，仅在设置ConditionTypeEnum类型为ASC或DESC时有效
     * @return
     */
    int sort() default 1;

    /**
     * sql中字段名称
     * @return
     */
    String column() default "";

    /**
     * 条件类型
     * @return
     */
    ConditionTypeEnum[] type() default ConditionTypeEnum.EQUALS;

    /**
     * 是否给条件对应的值加单引号
     * @return
     */
    boolean singleQuotes() default false;

    /**
     * 条件类型枚举
     */
    enum ConditionTypeEnum {

        /**
         * =
         */
        EQUALS,

        /**
         * like
         */
        LIKE,

        /**
         * asc
         */
        ASC,

        /**
         * desc
         */
        DESC
    }

}
