package com.hwy.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 生成代码参数bean
 * @date 2018/8/13 10:10
 **/
@Getter
@Builder
@AllArgsConstructor
public class CodeGeneratorBean {

    /**
     * 表名称列表
     */
    private List<String> tableNameList;

    /**
     * 所有字段的集合
     * key:表名, value:表字段信息列表
     */
    private Map<String, List<ColumnBean>> columnMap;

    /**
     * 所有表的集合
     * key:表名, value:表结构信息
     */
    private Map<String, TableBean> tableMap;

    /**
     * 模板列表
     */
    private List<TemplateBean> templateList;

    /**
     * 模板组
     */
    private TemplateGroupBean group;
}
