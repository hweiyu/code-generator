package com.hwy.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hwy.aop.DynamicSource;
import com.hwy.dao.CodeGeneratorMapper;
import com.hwy.dto.Page;
import com.hwy.dto.request.CodeGenReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.request.QueryReqDto;
import com.hwy.dto.response.TableResDto;
import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import com.hwy.model.TemplateModel;
import com.hwy.service.CodeGeneratorService;
import com.hwy.service.TemplateService;
import com.hwy.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

	@Autowired
	private CodeGeneratorMapper codeGeneratorMapper;

	@Autowired
	private TemplateService templateService;

	@DynamicSource
	@Override
	public PageResDto<TableResDto> list(Map<String, Object> map) {
		//查询列表数据
		QueryReqDto query = new QueryReqDto(map);
		List<TableResDto> result = new ArrayList<>(256);
		int total = codeGeneratorMapper.queryTotal(query);
		Page page = Page.builder().total(total).page(query.getPage()).limit(query.getLimit()).build();
		if (total > 0) {
			List<TableModel> models = codeGeneratorMapper.queryList(query);
			if (null != models) {
				for (TableModel tableModel : models) {
					result.add(TableResDto.get(tableModel));
				}
			}
		}
		return PageUtil.getPageInfo(result, page);
	}

	@DynamicSource
	@Override
	public byte[] generatorCode(CodeGenReqDto reqDto) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		//查表结构信息，因为数据不多所以直接查全表
		Map<String, TableModel> tableMap = getAllTable();
		//查表字段结构信息，因为数据不多所以直接查全表
		Multimap<String, ColumnModel> columnMap = getAllColumn();
		//查所有模板列表
		List<TemplateModel> templates = templateService.listByGroupId(reqDto.getGroupId());
		for(String tableName : reqDto.getTableNameList()){
			//查询表信息
			TableModel table = tableMap.get(tableName);
			//查询列信息
			List<ColumnModel> columns = (List<ColumnModel>) columnMap.get(tableName);
			//生成代码
			CodeGeneratorUtils.generatorCode(table, columns, templates, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	private Map<String, TableModel> getAllTable() {
		Map<String, TableModel> result = new HashMap<>(256);
		List<TableModel> tableModels = codeGeneratorMapper.queryAllTable();
		if (null != tableModels) {
			for (TableModel tableModel : tableModels) {
				result.put(tableModel.getTableName(), tableModel);
			}
		}
		return result;
	}

	private Multimap<String, ColumnModel> getAllColumn() {
		Multimap<String, ColumnModel> result = ArrayListMultimap.create();
		List<ColumnModel> columnModels = codeGeneratorMapper.queryAllColumns();
		if (null != columnModels) {
			for (ColumnModel columnModel : columnModels) {
				result.put(columnModel.getTableName(), columnModel);
			}
		}
		return result;
	}

}
