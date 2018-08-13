package com.hwy.bean.param;

import com.hwy.anno.SqlParam;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableParamBean extends SqlParamBean {

    @SqlParam(column = "table_name", type = SqlParam.ConditionTypeEnum.LIKE)
    private String tableName;

    @SqlParam(column = "group_id", type = SqlParam.ConditionTypeEnum.EQUALS)
    private Long groupId;

    @SqlParam(column = "table_schema", type = SqlParam.ConditionTypeEnum.EQUALS)
    private String tableSchema;
}
