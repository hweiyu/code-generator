package com.hwy.utils;

import com.hwy.entity.ParamEntity;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 10:58
 **/
public class ParamCacheUtil {

    private static volatile ParamEntity paramEntity = defaultParamEntity();

    private static final Object LOCK = new Object();

    public static ParamEntity defaultParamEntity() {
        return ParamEntity.builder()
                .author("demo")
                .module("generator")
                .javaPackage("com.hwy.modules")
                .tablePrefix("t_")
                .mainPath("com.hwy")
                .build();
    }

    public static ParamEntity get() {
        synchronized (LOCK) {
            return paramEntity;
        }
    }

    public static void set(ParamEntity paramEntity) {
        synchronized (LOCK) {
            ParamCacheUtil.paramEntity = paramEntity;
        }
    }

}
