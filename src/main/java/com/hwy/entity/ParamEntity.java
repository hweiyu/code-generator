package com.hwy.entity;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/9 20:42
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamEntity {

    private String author;

    private String module;

    private String javaPackage;

    private String tablePrefix;

    private String mainPath;
}
