package com.hwy.dto.request;

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
public class DataSourceQueryReqDto extends PageReq {

    private static final long serialVersionUID = 1124765859935956749L;

    /**
     * 数据源名称
     */
    private String dataSourceName;

}
