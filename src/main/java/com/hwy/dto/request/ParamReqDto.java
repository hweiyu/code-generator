package com.hwy.dto.request;

import com.hwy.dto.BaseReq;
import com.hwy.entity.ParamEntity;
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
public class ParamReqDto extends BaseReq {

    private static final long serialVersionUID = -6752484365552107541L;

    private String author;

    private String module;

    private String javaPackage;

    private String tablePrefix;

    private String mainPath;

    public ParamEntity to() {
        return ParamEntity.builder()
                .author(getAuthor())
                .module(getModule())
                .javaPackage(getJavaPackage())
                .tablePrefix(getTablePrefix())
                .mainPath(getMainPath())
                .build();
    }
}
