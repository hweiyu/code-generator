package com.hwy.bean.param;

import com.hwy.dto.Page;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 用于标记sql条件使用的bean
 * @date 2018/8/13 9:06
 **/
@Getter
@Setter
public class SqlParamBean {

    private Long sourceId;

    private Integer offset;

    private Integer limit;

    public SqlParamBean() {}

    public SqlParamBean(Long sourceId) {
        this.sourceId = sourceId;
    }

    public SqlParamBean setPage(Page page) {
        if (null != page) {
            int curPage = page.getPage() - 1;
            setOffset(curPage > 0 ? curPage : 0);
            setLimit(page.getLimit());
        }
        return this;
    }
}
