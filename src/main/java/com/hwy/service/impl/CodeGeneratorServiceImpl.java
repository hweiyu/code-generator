package com.hwy.service.impl;

import com.hwy.aop.DynamicSource;
import com.hwy.bean.CodeGeneratorBean;
import com.hwy.factory.CodeGeneratorHandlerFactory;
import com.hwy.factory.CodeGeneratorParamFactory;
import com.hwy.handler.CodeGeneratorHandler;
import com.hwy.mapper.CodeGeneratorMapper;
import com.hwy.dto.Page;
import com.hwy.dto.request.CodeGenReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.request.QueryReqDto;
import com.hwy.dto.response.TableResDto;
import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import com.hwy.model.TemplateGroupModel;
import com.hwy.model.TemplateModel;
import com.hwy.service.CodeGeneratorService;
import com.hwy.service.TemplateGroupService;
import com.hwy.service.TemplateService;
import com.hwy.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
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
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

	@Autowired
	private CodeGeneratorMapper codeGeneratorMapper;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private TemplateGroupService templateGroupService;

	@DynamicSource
	@Override
	public PageResDto<TableResDto> list(Map<String, Object> map) {
		//查询列表数据
		QueryReqDto query = new QueryReqDto(map);
		List<TableResDto> result = CollectionUtil.newArrayList();
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
		//生成压缩包
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		//封装参数
		CodeGeneratorBean param = getParam(reqDto);
		//获取处理器并生成代码
		getHandler(param, zip).generatorCode();
		//关闭压缩包
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	private CodeGeneratorBean getParam(CodeGenReqDto reqDto) {
		//查模板组信息
		TemplateGroupModel group = templateGroupService.getById(reqDto.getGroupId());
		//查所有模板列表
		List<TemplateModel> templates = templateService.listByGroupId(reqDto.getGroupId());
		//查表结构信息，因为数据不多所以直接查全表
		List<TableModel> tableModels = codeGeneratorMapper.queryAllTable();
		//查表字段结构信息，因为数据不多所以直接查全表
		List<ColumnModel> columnModels = codeGeneratorMapper.queryAllColumns();
		return CodeGeneratorParamFactory.builder()
				.group(group)
				.templateList(templates)
				.tableNameList(reqDto.getTableNameList())
				.tableList(tableModels)
				.columnList(columnModels)
				.build();
	}

	private CodeGeneratorHandler getHandler(CodeGeneratorBean param, ZipOutputStream zip) {
		return CodeGeneratorHandlerFactory.builder()
				.param(param)
				.zip(zip)
				.build()
				.createHandler();
	}
}
