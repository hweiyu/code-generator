package com.hwy.mapper;

import com.hwy.model.DataSourceModel;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 数据源 mapper
 * @date 2018/8/13 10:10
 **/
@Component
public interface DataSourceMapper extends Mapper<DataSourceModel> {
	
}
