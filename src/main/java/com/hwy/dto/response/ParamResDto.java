package com.hwy.dto.response;

import com.hwy.entity.ParamEntity;
import com.hwy.utils.ParamCacheUtil;
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
public class ParamResDto extends BaseRes {

    private static final long serialVersionUID = 6416676157252675780L;

    private String author;

    private String module;

    private String javaPackage;

    private String tablePrefix;

    private String mainPath;

    public static ParamResDto get(ParamEntity entity) {
        if (null == entity) {
            entity = ParamCacheUtil.defaultParamEntity();
        }
        return ParamResDto.builder()
                .author(entity.getAuthor())
                .module(entity.getModule())
                .javaPackage(entity.getJavaPackage())
                .tablePrefix(entity.getTablePrefix())
                .mainPath(entity.getMainPath())
                .build();
    }
}
