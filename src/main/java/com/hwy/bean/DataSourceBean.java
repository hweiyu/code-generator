package com.hwy.bean;

import com.alibaba.druid.pool.DruidDataSource;
import com.hwy.model.DataSourceModel;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 数据源信息bean
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceBean {

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * jdbc驱动类名
     */
    private String driverClassName;

    /**
     * 数据库连接
     */
    private String url;

    /**
     * 数据库
     */
    private String dbName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    public static DataSourceBean get(DataSourceModel model) {
        return DataSourceBean.builder()
                .dataSourceName(model.getDataSourceName())
                .driverClassName(model.getDriverClassName())
                .url(model.getUrl())
                .dbName(model.getDbName())
                .userName(model.getUserName())
                .userPassword(model.getUserPassword())
                .build();
    }

    public DruidDataSource toDruidDataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setUrl(getCompleteUrl());
        source.setUsername(userName);
        source.setPassword(userPassword);
        source.setDriverClassName(driverClassName);
        return source;
    }

    public String getCompleteUrl() {
        String template = "%s/%s?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false";
        return String.format(template, getUrl(), getDbName());
    }
}
