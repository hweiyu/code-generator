package com.hwy.factory;

import com.hwy.bean.param.SqlParamBean;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: sql参数工厂
 * @date 2018/8/13 9:31
 **/
public class SqlParamFactory {

    private BeanPropertySqlParameterSource param;

    public static SqlParamFactory builder() {
        return new SqlParamFactory();
    }

    public SqlParamFactory param(SqlParamBean paramBean) {
        param = new BeanPropertySqlParameterSource(paramBean);
        return this;
    }

    public BeanPropertySqlParameterSource build() {
        return param;
    }
}
