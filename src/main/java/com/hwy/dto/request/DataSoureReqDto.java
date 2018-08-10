package com.hwy.dto.request;

import com.hwy.entity.DataSourceEntity;
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
public class DataSoureReqDto extends BaseReq {

    private static final long serialVersionUID = -2227604190655358489L;

    private String ip;

    private String port;

    private String database;

    private String userName;

    private String password;

    public DataSourceEntity to() {
        return DataSourceEntity.builder()
                .ip(getIp())
                .port(getPort())
                .database(getDatabase())
                .userName(getUserName())
                .password(getPassword())
                .build();
    }
}
