package com.hwy.utils;

import com.hwy.bean.DataSourceBean;
import com.hwy.bean.param.SqlParamBean;
import com.hwy.exception.CodeGenException;
import com.hwy.factory.JdbcFactory;
import com.hwy.factory.SqlParamFactory;
import com.hwy.factory.SqlWrapFactory;
import com.hwy.model.DataSourceModel;
import com.hwy.service.DataSourceService;
import com.hwy.service.impl.DataSourceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: jdbc工具类
 * @date 2018/8/9 20:14
 **/
@Slf4j
public class JdbcUtil {

    /**
     * 连接测试
     * @param source
     */
    public static void tryConnect(DataSourceBean source) {
        Connection con = null;
        boolean tryError = false;
        try {
            Class.forName(source.getDriverClassName());
            con = DriverManager.getConnection(
                    source.getCompleteUrl(), source.getUserName(), source.getUserPassword());
        } catch (Exception e) {
            log.error("数据库连接失败");
            tryError = true;
        } finally {
            if (null != con) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error("数据库连接失败");
                    tryError = true;
                }
            }
        }
        if (tryError) {
            throw new CodeGenException("连接失败");
        }
    }

    /**
     * 查记录数
     * @param sql
     * @param param
     * @return
     */
    public static int queryForInt(String sql, SqlParamBean param) {
        Integer res = getJdbcTemplate(param.getSourceId())
                .queryForObject(
                        SqlWrapFactory.builder().sql(sql).condition(param).build(),
                        SqlParamFactory.builder().param(new SqlParamBean()).build(),
                        Integer.class);
        return LangUtils.nvl(res, 0);
    }

    /**
     * 查列表
     * @param sql
     * @param param
     * @param resultType
     * @param <T>
     * @return
     */
    public static <T> List<T> queryForList(String sql, SqlParamBean param, Class<T> resultType) {
        List<T> result = getJdbcTemplate(param.getSourceId())
                .query(
                        SqlWrapFactory.builder().sql(sql).condition(param).build(),
                        SqlParamFactory.builder().param(param).build(),
                        new BeanPropertyRowMapper<>(resultType));
       return CollectionUtil.newArrayList(result);
    }

    private static NamedParameterJdbcTemplate getJdbcTemplate(Long sourceId) {
        AssertUtil.notNull(sourceId, "数据源为空");
        DataSourceService dataSourceService = ApplicationUtil.getBean(DataSourceServiceImpl.class);
        DataSourceModel model = dataSourceService.getById(sourceId);
        AssertUtil.notNull(model, "数据源为空");
        DataSourceBean source = DataSourceBean.get(model);
        tryConnect(source);
        return JdbcFactory.builder().source(source).build();
    }

}
