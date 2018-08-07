package com.hwy.service;

import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;

import java.util.List;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/7 14:51
 **/
public interface SysGeneratorService {

    List<TableModel> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    TableModel queryTable(String tableName);

    List<ColumnModel> queryColumns(String tableName);

    byte[] generatorCode(String[] tableNames);
}
