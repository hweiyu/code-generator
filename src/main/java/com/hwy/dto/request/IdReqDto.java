package com.hwy.dto.request;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: id请求dto
 * @date 2018/8/10 11:45
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdReqDto extends BaseReq {

    private static final long serialVersionUID = -6356663987957698790L;

    /**
     * 主键id
     */
    private Long id;
}
