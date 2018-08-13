package com.hwy.dto.request;

import com.hwy.bean.param.TableParamBean;
import lombok.*;

/**
 * 数据库源返回dto
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
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

    private String tableName;

    private Integer offset;

    private Integer limit;

    public TableParamBean toTableParamBean() {
        TableParamBean bean = new TableParamBean();
        bean.setSourceId(sourceId);
        bean.setTableName(tableName);
        bean.setTableSchema("(select database())");
        return bean;
    }
}
