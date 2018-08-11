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
public class TemplateQueryReqDto extends PageReq {

    private static final long serialVersionUID = 1124765859935956749L;

    /**
     * 模板名称
     */
    private String template;

    /**
     * 模板类型
     */
    private Integer type;

    private Integer groupId;

}
