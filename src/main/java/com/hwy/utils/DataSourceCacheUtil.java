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

    private static DataSourceEntity dataSource = defaultDataSource();

    private static boolean hasSetting = false;

    public static DataSourceEntity defaultDataSource() {
        return DataSourceEntity.builder()
                .ip("127.0.0.1")
                .port("3306")
                .database("demo")
                .userName("root")
                .password("123456")
                .build();
    }

    public static synchronized DataSourceEntity get() {
        return dataSource;
    }

    public static synchronized void set(DataSourceEntity dataSourceEntity) {
        dataSource = dataSourceEntity;
        if (!hasSetting) {
            hasSetting = true;
        }
    }

    public static synchronized boolean hasSetting() {
        return hasSetting;
    }

}
