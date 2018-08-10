package com.hwy.utils;

import com.hwy.dto.Page;
import com.hwy.dto.response.PageResDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/10 13:49
 **/
public class PageUtil {

    public static <T> PageResDto<T> getPageInfo(List<T> list, Page page) {
        PageResDto<T> pageInfo = new PageResDto<>();
        pageInfo.setList(list);
        pageInfo.setTotalCount(page.getTotal());
        pageInfo.setPageSize(page.getLimit());
        pageInfo.setCurrPage(page.getPage());
        pageInfo.setTotalPage((int) Math.ceil((double) page.getTotal() / page.getLimit()));
        return pageInfo;
    }

}
