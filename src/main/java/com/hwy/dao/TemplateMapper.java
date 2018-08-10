package com.hwy.dao;

import com.hwy.model.TemplateModel;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * 模板 mapper
 * 
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Component
public interface TemplateMapper extends Mapper<TemplateModel> {
	
}
