package com.hwy.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/7 15:23
 **/
@Setter
@Getter
public class ColumnModel {

    private String columnName;

    private String dataType;

    private String columnComment;

    private String columnKey;

    private String extra;
}
