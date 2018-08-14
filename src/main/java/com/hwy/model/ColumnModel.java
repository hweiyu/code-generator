package com.hwy.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 表字段信息model
 * @date 2018/8/7 15:23
 **/
@Setter
@Getter
public class ColumnModel {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段备注
     */
    private String columnComment;

    /**
     * key
     */
    private String columnKey;

    /**
     * 额外信息
     */
    private String extra;
}
