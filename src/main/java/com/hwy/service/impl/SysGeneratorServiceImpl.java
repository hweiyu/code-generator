package com.hwy.service.impl;

import com.hwy.dao.SysGeneratorDao;
import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import com.hwy.service.SysGeneratorService;
import com.hwy.utils.GenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Service
@Slf4j
public class SysGeneratorServiceImpl implements SysGeneratorService {

	@Autowired
	private SysGeneratorDao sysGeneratorDao;

	@Override
	public List<TableModel> queryList(Map<String, Object> map) {
		return sysGeneratorDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysGeneratorDao.queryTotal(map);
	}

	@Override
	public TableModel queryTable(String tableName) {
		return sysGeneratorDao.queryTable(tableName);
	}

	@Override
	public List<ColumnModel> queryColumns(String tableName) {
		return sysGeneratorDao.queryColumns(tableName);
	}

	@Override
	public byte[] generatorCode(String[] tableNames) {
		try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(outputStream)) {
			for(String tableName : tableNames){
				//查询表信息
				TableModel table = queryTable(tableName);
				//查询列信息
				List<ColumnModel> columns = queryColumns(tableName);
				//生成代码
				GenUtils.generatorCode(table, columns, zip);
			}
			return outputStream.toByteArray();
		} catch (Exception e) {
			log.error("生成代码失败", e);
		}
		return new byte[0];
	}
}