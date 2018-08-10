package com.hwy.aop;

import com.alibaba.druid.pool.DruidDataSource;
import com.hwy.config.DynamicDataSource;
import com.hwy.utils.CodeGeneratorException;
import com.hwy.utils.DataSourceCacheUtil;
import com.hwy.utils.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

    @Around("@annotation(com.hwy.aop.DynamicSource))")
    public Object invoke(ProceedingJoinPoint point) {
        try {
//            tryConnect();
//            setDataSource();
            return point.proceed();
        } catch (CodeGeneratorException e) {
            throw e;
        } catch (Throwable t) {
            log.error("操作失败", t);
            throw new CodeGeneratorException("操作失败");
        }
    }

    private void setDataSource() {
        DruidDataSource currentDataSource = new DruidDataSource();
        currentDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        currentDataSource.setUrl(DataSourceCacheUtil.get().getUrl());
        currentDataSource.setUsername(DataSourceCacheUtil.get().getUserName());
        currentDataSource.setPassword(DataSourceCacheUtil.get().getPassword());
        //连接异常重连次数
        currentDataSource.setConnectionErrorRetryAttempts(0);
        //拿到动态切换数据源对象
        Map<Object, Object> dataSourceMap = new HashMap<>(16);
        dataSourceMap.put("dynamic", currentDataSource);
        DynamicDataSource.get().setTargetDataSources(dataSourceMap);
    }

    private void tryConnect() {
        if (!JdbcUtil.tryConnect(DataSourceCacheUtil.get())) {
            throw new CodeGeneratorException("连接失败");
        }
    }
}
