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

    private List<String> tableNameList;

    private Map<String, List<ColumnBean>> columnMap;

    private Map<String, TableBean> tableMap;

    private List<TemplateBean> templateList;

    private TemplateGroupBean group;
}
