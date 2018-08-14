package com.hwy.dto.response;

import com.hwy.model.DataSourceModel;

import lombok.*;

/**
 * 数据库源请求dto
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceResDto extends BaseRes {

    private static final long serialVersionUID = 5694081829340127523L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * jdbc驱动类名
     */
    private String driverClassName;

    /**
     * 数据库连接
     */
    private String url;

    /**
     * 数据库
     */
    private String dbName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 0:禁用,1:启用,2:已删除
     */
    private Integer dataStatus;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    public static DataSourceResDto get(DataSourceModel model) {
        return DataSourceResDto.builder()
                .id(model.getId())
                .dataSourceName(model.getDataSourceName())
                .driverClassName(model.getDriverClassName())
                .url(model.getUrl())
                .dbName(model.getDbName())
                .userName(model.getUserName())
                .userPassword(model.getUserPassword())
                .dataStatus(model.getDataStatus())
                .createTime(model.getCreateTime())
                .updateTime(model.getUpdateTime())
                .build();
    }

    public static DataSourceResDto getWithPasswordHide(DataSourceModel model) {
        DataSourceResDto resDto = get(model);
        resDto.setUserPassword("******");
        return resDto;
    }
}
