package com.hwy.dto.response;

import com.hwy.dto.BaseRes;
import com.hwy.entity.DataSourceEntity;
import com.hwy.utils.DataSourceCacheUtil;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 10:51
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSoureResDto extends BaseRes {

    private static final long serialVersionUID = -7532239555984137243L;

    private String ip;

    private String port;

    private String database;

    private String userName;

    private String password;

    public static DataSoureResDto get(DataSourceEntity entity) {
        if (null == entity) {
            entity = DataSourceCacheUtil.defaultDataSource();
        }
        return DataSoureResDto.builder()
                .ip(entity.getIp())
                .port(entity.getPort())
                .database(entity.getDatabase())
                .userName(entity.getUserName())
                .password(entity.getPassword())
                .build();
    }
}
