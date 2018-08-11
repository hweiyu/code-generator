package com.hwy.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * 异常处理器
 *
 * @author huangweiyu
 * @version V1.0
 * @date 10:27
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
