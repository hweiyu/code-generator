package com.hwy.dto.request;

import com.hwy.bean.param.TableParamBean;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 生成代码查询条件dto
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeGenQueryReqDto extends PageReq {

    private static final long serialVersionUID = 1124765859935956749L;

    /**
     * 数据源名称
     */
    private Long sourceId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 当前页
     */
    private Integer offset;

    /**
     * 页显示数
     */
    private Integer limit;

    public TableParamBean toTableParamBean() {
        TableParamBean bean = new TableParamBean();
        bean.setSourceId(sourceId);
        bean.setTableName(tableName);
        bean.setTableSchema("(select database())");
        return bean;
    }
}
