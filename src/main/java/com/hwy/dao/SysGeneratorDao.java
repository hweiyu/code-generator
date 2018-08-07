package com.hwy.dao;

import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Component
public interface SysGeneratorDao {

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
	 * @param tableName
	 * @return
	 */
	TableModel queryTable(String tableName);

	/**
	 * 查表字段结构
	 * @param tableName
	 * @return
	 */
	List<ColumnModel> queryColumns(String tableName);
}