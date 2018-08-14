package com.hwy.dto;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 分页
 * @date 2018/8/10 13:56
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    /**
     * 当前页
     */
    private int page;

    /**
     * 每页显示数量
     */
    private int limit;

    /**
     * 总页数
     */
    private int total;
}
