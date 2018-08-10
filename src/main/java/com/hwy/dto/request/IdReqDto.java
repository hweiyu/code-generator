package com.hwy.dto.request;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/10 11:45
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdReqDto extends BaseReq {

    private static final long serialVersionUID = -6356663987957698790L;

    private Long id;
}