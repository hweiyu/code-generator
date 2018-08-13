package com.hwy.factory;

import com.hwy.bean.DataSourceBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/13 9:21
 **/
public class JdbcFactory {

    private final ThreadLocal<NamedParameterJdbcTemplate> jdbcTemplate = new ThreadLocal<>();

    public static JdbcFactory builder() {
        return new JdbcFactory();
    }

    public JdbcFactory source(DataSourceBean sourceBean) {
        setJdbcTemplate(sourceBean);
        return this;
    }

    public NamedParameterJdbcTemplate build() {
        return getJdbcTemplate();
    }

    private void setJdbcTemplate(DataSourceBean sourceBean) {
        jdbcTemplate.set(new NamedParameterJdbcTemplate(sourceBean.toDruidDataSource()));
    }

    private NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate.get();
    }

    public void clearJdbcTemplate() {
        jdbcTemplate.remove();
    }
}
