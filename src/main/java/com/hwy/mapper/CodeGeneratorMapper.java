package com.hwy.mapper;

import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 代码生成 mapper
 * @date 2018/8/13 10:10
 **/
@Component
public interface CodeGeneratorMapper {

	/**
	 * 表列表
	 * @param map
	 * @return
	 */
	List<TableModel> queryList(Map<String, Object> map);

	/**
	 * 表列表数据总量
	 * @param map
	 * @return
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 查表结构
	 *
	 * @return
	 */
	List<TableModel> queryAllTable();

	/**
	 * 查表字段结构
	 *
	 * @return
	 */
	List<ColumnModel> queryAllColumns();
}
