package com.hwy.dto;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/10 13:56
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    private int page;

    private int limit;

    private int total;
}
