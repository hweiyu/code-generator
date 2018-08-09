package com.hwy.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Getter
@Setter
public class QueryReqDto extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 2650725986011599517L;

    /**
     * 当前页码
     */
    private int page;

    /**
     * 每页条数
     */
    private int limit;

    public QueryReqDto(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }

}
