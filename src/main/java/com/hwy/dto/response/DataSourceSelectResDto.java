package com.hwy.dto.response;

import com.hwy.model.DataSourceModel;
import lombok.*;

/**
 * 模板组返回dto
 *
 * @author hweiyu
 * @date 2018-08-10 22:32:56
 */
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
