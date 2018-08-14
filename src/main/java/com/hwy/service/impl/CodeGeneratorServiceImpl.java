package com.hwy.service.impl;

import com.hwy.bean.CodeGeneratorBean;
import com.hwy.bean.param.SqlParamBean;
import com.hwy.bean.param.TableParamBean;
import com.hwy.dto.request.CodeGenQueryReqDto;
import com.hwy.factory.CodeGeneratorHandlerFactory;
import com.hwy.factory.CodeGeneratorParamFactory;
import com.hwy.handler.CodeGeneratorHandler;
import com.hwy.mapper.CodeGeneratorMapper;
import com.hwy.dto.Page;
import com.hwy.dto.request.CodeGenReqDto;
import com.hwy.dto.response.PageResDto;
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
import java.util.zip.ZipOutputStream;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 代码生成服务
 * @date 2018/8/13 10:10
 **/
@Service
@Slf4j
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private TemplateGroupService templateGroupService;

	/**
	 * 表信息列表
	 * @param reqDto
	 * @return
	 */
	@Override
	public PageResDto<TableResDto> list(CodeGenQueryReqDto reqDto) {
		//查询列表数据
		List<TableResDto> result = CollectionUtil.newArrayList();
		TableParamBean param = reqDto.toTableParamBean();
		int total = JdbcUtil.queryForInt("select count(*) from information_schema.tables", param);
		Page page = Page.builder().total(total).page(reqDto.getPage()).limit(reqDto.getLimit()).build();
		if (total > 0) {
			param.setPage(page);
			List<TableModel> models = JdbcUtil.queryForList("select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables", param, TableModel.class);
			if (null != models) {
				for (TableModel tableModel : models) {
					result.add(TableResDto.get(tableModel));
				}
			}
		}
		return PageUtil.getPageInfo(result, page);
	}

	/**
	 * 生成代码
	 * @param reqDto
	 * @return
	 */
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
		List<TableModel> tableModels = JdbcUtil.queryForList("select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database())",
				new SqlParamBean(reqDto.getSourceId()), TableModel.class);
		//查表字段结构信息，因为数据不多所以直接查全表
		List<ColumnModel> columnModels = JdbcUtil.queryForList("select table_name tableName, column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_schema = (select database()) order by ordinal_position",
				new SqlParamBean(reqDto.getSourceId()), ColumnModel.class);
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
