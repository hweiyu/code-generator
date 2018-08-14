package com.hwy.dto.response;

import com.hwy.model.DataSourceModel;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 数据源查询返回dto
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceSelectResDto extends BaseRes {

    private static final long serialVersionUID = 1867275878457507761L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    public static DataSourceSelectResDto get(DataSourceModel model) {
        return DataSourceSelectResDto.builder()
                .id(model.getId())
                .dataSourceName(model.getDataSourceName())
                .build();
    }
}
