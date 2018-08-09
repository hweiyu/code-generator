package com.hwy.utils;

import com.hwy.entity.DataSourceEntity;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 10:58
 **/
public class DataSourceCacheUtil {

    private static volatile DataSourceEntity dataSource = defaultDataSource();

    private static volatile boolean hasSetting = false;

    private static final Object LOCK = new Object();

    public static DataSourceEntity defaultDataSource() {
        return DataSourceEntity.builder()
                .ip("127.0.0.1")
                .port("3306")
                .database("demo")
                .userName("root")
                .password("123456")
                .build();
    }

    public static DataSourceEntity get() {
        synchronized (LOCK) {
            return dataSource;
        }
    }

    public static void set(DataSourceEntity dataSourceEntity) {
        synchronized (LOCK) {
            dataSource = dataSourceEntity;
            if (!hasSetting) {
                hasSetting = true;
            }
        }
    }

    public static boolean hasSetting() {
        synchronized (LOCK) {
            return hasSetting;
        }
    }

}
