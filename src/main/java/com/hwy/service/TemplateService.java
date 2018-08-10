package com.hwy.service;

import com.hwy.dto.request.TemplateQueryReqDto;
import com.hwy.dto.request.TemplateReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateResDto;

/**
 * 模板接口
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
public interface TemplateService {

    /**
     * 列表
    */
    PageResDto<TemplateResDto> select(TemplateQueryReqDto reqDto);

    /**
    * 查询
    */
    TemplateResDto get(TemplateReqDto reqDto);

    /**
    * 添加
    */
    int insert(TemplateReqDto reqDto);

    /**
    * 修改
    */
    int update(TemplateReqDto reqDto);

    /**
    * 删除
    */
    int delete(TemplateReqDto reqDto);
}

