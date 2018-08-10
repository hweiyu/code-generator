package com.hwy.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 *
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResDto<T> implements Serializable {

    private static final long serialVersionUID = -2163784333406337161L;

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 当前页数
     */
    private int currPage;

    /**
     * 列表数据
     */
    private List<T> list;

}
