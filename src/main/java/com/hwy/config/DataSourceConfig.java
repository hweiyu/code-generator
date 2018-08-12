package com.hwy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.hwy.utils.CollectionUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 17:57
 **/
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    /**
     * 配置动态数据源
     *
     * @return data source
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamic = DynamicDataSource.get();
        DruidDataSource defaultDataSource = new DruidDataSource();
        Map<Object, Object> dataSourceMap = CollectionUtil.newHashMap();
        defaultDataSource.setUrl(url);
        defaultDataSource.setUsername(userName);
        defaultDataSource.setPassword(password);
        defaultDataSource.setDriverClassName(driverClassName);
        dataSourceMap.put(DynamicDataSource.DEFAULT_DATA_SOURCE, defaultDataSource);
        dynamic.setTargetDataSources(dataSourceMap);
        dynamic.setDefaultTargetDataSource(defaultDataSource);
        return dynamic;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}
