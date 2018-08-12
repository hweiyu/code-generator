package com.hwy.aop;

import com.alibaba.druid.pool.DruidDataSource;
import com.hwy.config.DynamicDataSource;
import com.hwy.exception.CodeGenException;
import com.hwy.service.DataSourceService;
import com.hwy.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 18:06
 **/
@Aspect
@Order(-1)
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Autowired
    private DataSourceService dataSourceService;

    @Around("@annotation(com.hwy.aop.DynamicSource))")
    public Object invoke(ProceedingJoinPoint point) {
        try {
            tryConnect();
            setDataSource();
            return point.proceed();
        } catch (CodeGenException e) {
            throw e;
        } catch (Throwable t) {
            log.error("操作失败", t);
            throw new CodeGenException("操作失败");
        } finally {

        }
    }

    private void setDataSource() {
        DruidDataSource currentDataSource = new DruidDataSource();
        currentDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        currentDataSource.setUrl(DataSourceCacheUtil.get().getUrl());
//        currentDataSource.setUsername(DataSourceCacheUtil.get().getUserName());
//        currentDataSource.setPassword(DataSourceCacheUtil.get().getPassword());
        //连接异常重连次数
        currentDataSource.setConnectionErrorRetryAttempts(0);
        //拿到动态切换数据源对象
        Map<Object, Object> dataSourceMap = CollectionUtil.newHashMap();
        dataSourceMap.put(DynamicDataSource.DYNAMIC_DATA_SOURCE, currentDataSource);
        DynamicDataSource.get().setTargetDataSources(dataSourceMap);
    }

    private void tryConnect() {
//        if (!JdbcUtil.tryConnect(DataSourceCacheUtil.get())) {
//            throw new CodeGeneratorException("连接失败");
//        }
    }
}
