package com.hwy.service;

import com.hwy.dto.request.TemplateQueryReqDto;
import com.hwy.dto.request.TemplateReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateResDto;
import com.hwy.model.TemplateModel;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板接口
 * @date 2018/8/13 10:10
 **/
public interface TemplateService {

    /**
     * 列表
    */
    PageResDto<TemplateResDto> select(TemplateQueryReqDto reqDto);

    /**
    * 查询
    */
    TemplateResDto get(TemplateReqDto reqDto);

    TemplateModel getById(Long id);

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

    List<TemplateModel> listByGroupId(Long groupId);

    List<TemplateResDto> genList(Long groupId);
}

