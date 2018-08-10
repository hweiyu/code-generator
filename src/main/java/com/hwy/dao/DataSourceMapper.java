package com.hwy.dao;

import com.hwy.model.DataSourceModel;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * 数据库源 mapper
 * 
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Component
public interface DataSourceMapper extends Mapper<DataSourceModel> {
	
}
