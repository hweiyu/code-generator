package com.hwy.dto.request;

import com.hwy.dto.Page;
import com.hwy.utils.LangUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 分页请求dto
 * @date 2018/8/10 11:43
 **/
@Getter
@Setter
public class PageReq extends BaseReq {

    private static final long serialVersionUID = -1113645217731393036L;

    private Integer page;

    private Integer limit;

    public Page to() {
        return to(0);
    }

    public Page to(int total) {
        return Page.builder()
                .page(LangUtils.nvl(page, 1))
                .limit(LangUtils.nvl(limit, 10))
                .total(total)
                .build();
    }
}
