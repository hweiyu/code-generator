package com.hwy.dto.response;

import com.hwy.dto.BaseRes;
import com.hwy.model.TableModel;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 15:00
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableResDto extends BaseRes {

    private static final long serialVersionUID = 8536747378771813313L;

    private String tableName;

    private String engine;

    private String tableComment;

    private String createTime;

    public static TableResDto get(TableModel model) {
        return TableResDto.builder()
                .tableName(model.getTableName())
                .engine(model.getEngine())
                .tableComment(model.getTableComment())
                .createTime(model.getCreateTime())
                .build();
    }
}