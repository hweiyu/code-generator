package com.hwy.utils;

import com.hwy.model.DataSourceModel;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 20:14
 **/
@Slf4j
public class JdbcUtil {

    public static boolean tryConnect(DataSourceModel dataSourceModel) {
        Connection con = null;
        try {
            Class.forName(dataSourceModel.getDriverClassName());
            con = DriverManager.getConnection(
                    getUrl(dataSourceModel), dataSourceModel.getUserName(), dataSourceModel.getUserPassword());
            return true;
        } catch (Exception e) {
            log.error("数据库连接失败");
        } finally {
            if (null != con) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error("数据库连接失败");
                }
            }
        }
        return false;
    }

    private static String getUrl(DataSourceModel model) {
        String template = "%s/%s?useUnicode=true&characterEncoding=UTF-8";
        return String.format(template, model.getUrl(), model.getDbName());
    }
}
