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
