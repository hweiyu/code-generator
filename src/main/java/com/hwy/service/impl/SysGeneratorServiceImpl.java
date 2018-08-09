package com.hwy.service.impl;

import com.hwy.dao.SysGeneratorDao;
import com.hwy.dto.PageInfo;
import com.hwy.dto.request.DataSoureReqDto;
import com.hwy.dto.request.QueryReqDto;
import com.hwy.dto.response.DataSoureResDto;
import com.hwy.dto.response.TableResDto;
import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import com.hwy.service.SysGeneratorService;
import com.hwy.utils.CodeGeneratorException;
import com.hwy.utils.CodeGeneratorUtils;
import com.hwy.utils.DataSourceCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public PageInfo<TableResDto> list(Map<String, Object> map) {
		if (!DataSourceCacheUtil.hasSetting()) {
			throw new CodeGeneratorException("请先设置数据源");
		}
		//查询列表数据
		QueryReqDto query = new QueryReqDto(map);
		int total = sysGeneratorDao.queryTotal(query);
		List<TableModel> models = sysGeneratorDao.queryList(map);
		List<TableResDto> result = new ArrayList<>();
		if (null != models) {
			for (TableModel tableModel : models) {
				result.add(TableResDto.get(tableModel));
			}
		}
		return new PageInfo<>(result, total, query.getLimit(), query.getPage());
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
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			//查询表信息
			TableModel table = queryTable(tableName);
			//查询列信息
			List<ColumnModel> columns = queryColumns(tableName);
			//生成代码
			CodeGeneratorUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	@Override
	public DataSoureResDto getCacheConfig() {
		return DataSoureResDto.get(DataSourceCacheUtil.get());
	}

	@Override
	public void saveCacheConfig(DataSoureReqDto reqDto) {
		DataSourceCacheUtil.set(reqDto.to());
	}

	@Override
	public boolean connectTest(DataSoureReqDto reqDto) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					getUrl(reqDto), reqDto.getUserName(), reqDto.getPassword());
			return true;
		} catch (Exception e) {
			log.error("数据库连接失败");
		} finally {
			if (null != con) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("数据库连接失败");
				}
			}
		}
		return false;
	}

	private String getUrl(DataSoureReqDto reqDto) {
		String template = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8";
		return String.format(template, reqDto.getIp(), reqDto.getPort(), reqDto.getDatabase());
	}
}
