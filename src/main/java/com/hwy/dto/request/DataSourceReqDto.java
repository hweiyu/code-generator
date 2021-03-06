package com.hwy.dto.request;

import com.hwy.bean.DataSourceBean;
import com.hwy.model.DataSourceModel;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 数据源请求dto
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceReqDto extends BaseReq {

    private static final long serialVersionUID = 1124765859935956749L;

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

    public DataSourceModel to() {
        return DataSourceModel.builder()
                .id(getId())
                .dataSourceName(getDataSourceName())
                .driverClassName(getDriverClassName())
                .url(getUrl())
                .dbName(getDbName())
                .userName(getUserName())
                .userPassword(getUserPassword())
                .dataStatus(getDataStatus())
                .build();
    }

    public DataSourceBean toBean() {
        return DataSourceBean.builder()
                .dataSourceName(getDataSourceName())
                .driverClassName(getDriverClassName())
                .url(getUrl())
                .dbName(getDbName())
                .userName(getUserName())
                .userPassword(getUserPassword())
                .build();
    }
}
