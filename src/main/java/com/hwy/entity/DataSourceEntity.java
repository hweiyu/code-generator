package com.hwy.entity;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 10:58
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceEntity {

    private String ip;

    private String port;

    private String database;

    private String userName;

    private String password;

    public String getUrl() {
        String template = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8";
        return String.format(template, getIp(), getPort(), getDatabase());
    }
}
