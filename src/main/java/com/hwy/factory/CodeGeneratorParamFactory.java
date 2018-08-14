package com.hwy.factory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hwy.bean.*;
import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import com.hwy.model.TemplateGroupModel;
import com.hwy.model.TemplateModel;
import com.hwy.utils.AssertUtil;
import com.hwy.utils.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 构造生成代码参数工厂
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
public class CodeGeneratorParamFactory {

    private TemplateGroupBean group;

    private List<String> tableNameList;

    private Map<String, List<ColumnBean>> columnMap = CollectionUtil.newHashMap(512);

    private Map<String, TableBean> tableMap = CollectionUtil.newHashMap(256);

    private List<TemplateBean> templateList = CollectionUtil.newArrayList();

    public static CodeGeneratorParamFactory builder() {
        return new CodeGeneratorParamFactory();
    }

    public CodeGeneratorParamFactory group(TemplateGroupModel groupModel) {
        AssertUtil.notNull(groupModel, "模板组信息为空");
        setGroup(TemplateGroupBean.get(groupModel));
        return this;
    }

    public CodeGeneratorParamFactory tableNameList(List<String> tableNameList) {
        AssertUtil.notNull(tableNameList, "表名为空");
        setTableNameList(tableNameList);
        return this;
    }

    public CodeGeneratorParamFactory columnList(List<ColumnModel> columnList) {
        AssertUtil.notNull(columnList, "表字段信息为空");
        Multimap<String, ColumnBean> multimap = ArrayListMultimap.create();
        for (ColumnModel columnModel : columnList) {
            multimap.put(columnModel.getTableName(), ColumnBean.get(columnModel));
        }
        for (Map.Entry<String, Collection<ColumnBean>> entry : multimap.asMap().entrySet()) {
            columnMap.put(entry.getKey(), (List<ColumnBean>) entry.getValue());
        }
        return this;
    }

    public CodeGeneratorParamFactory tableList(List<TableModel> tableList) {
        AssertUtil.notNull(tableList, "表信息为空");
        for (TableModel tableModel : tableList) {
            tableMap.put(tableModel.getTableName(), TableBean.get(tableModel));
        }
        return this;
    }

    public CodeGeneratorParamFactory templateList(List<TemplateModel> templateModelList) {
        AssertUtil.notNull(templateModelList, "模板信息为空");
        for (TemplateModel templateModel : templateModelList) {
            templateList.add(TemplateBean.get(templateModel));
        }
        return this;
    }

    public CodeGeneratorBean build() {
        return CodeGeneratorBean.builder()
                .group(getGroup())
                .tableNameList(getTableNameList())
                .columnMap(getColumnMap())
                .tableMap(getTableMap())
                .templateList(getTemplateList())
                .build();
    }
}
