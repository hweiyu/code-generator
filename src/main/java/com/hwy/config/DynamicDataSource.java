package com.hwy.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 17:59
 **/
public class DynamicDataSource extends AbstractRoutingDataSource{

    private static volatile DynamicDataSource dynamicDataSource;

    private static final Object LOCK = new Object();

    public static DynamicDataSource get() {
        if (null == dynamicDataSource) {
            synchronized (LOCK) {
                if (null == dynamicDataSource) {
                    dynamicDataSource = new DynamicDataSource();
                }
            }
        }
        return dynamicDataSource;
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return "dynamic";
    }
}
