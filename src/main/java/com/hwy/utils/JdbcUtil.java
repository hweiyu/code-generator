package com.hwy.utils;

import com.hwy.bean.DataSourceBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 20:14
 **/
@Slf4j
public class JdbcUtil {

    public static boolean tryConnect(DataSourceBean sourceBean) {
        Connection con = null;
        try {
            Class.forName(sourceBean.getDriverClassName());
            con = DriverManager.getConnection(
                    getUrl(sourceBean), sourceBean.getUserName(), sourceBean.getUserPassword());
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

    public static int queryForInt(String sql, DataSourceBean source) {
        List<Map<String, Object>> result = getJdbcTemplate(source).queryForList(sql);
        return CollectionUtil.listSize(result);
    }

    public static <T> List<T> queryForList(String sql, Class<T> elementType, DataSourceBean source) {
       return getJdbcTemplate(source).queryForList(sql, elementType);
    }

    private static JdbcTemplate getJdbcTemplate(DataSourceBean sourceBean) {
        tryConnect(sourceBean);
        return new JdbcTemplate(sourceBean.toDruidDataSource());
    }

    private static String getUrl(DataSourceBean bean) {
        String template = "%s/%s?useUnicode=true&characterEncoding=UTF-8";
        return String.format(template, bean.getUrl(), bean.getDbName());
    }
}
