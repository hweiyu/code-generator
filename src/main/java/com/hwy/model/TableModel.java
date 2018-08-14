package com.hwy.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 表结构信息model
 * @date 2018/8/7 15:23
 **/
@Setter
@Getter
public class TableModel {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 存储引擎
     */
    private String engine;

    /**
     * 表备注
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private String createTime;
}
