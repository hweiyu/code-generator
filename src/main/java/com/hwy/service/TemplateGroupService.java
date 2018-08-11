package com.hwy.service;

import com.hwy.dto.request.TemplateGroupQueryReqDto;
import com.hwy.dto.request.TemplateGroupReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateGroupResDto;
import com.hwy.dto.response.TemplateGroupSelectResDto;
import com.hwy.model.TemplateGroupModel;

import java.util.List;

/**
 * 数据库源接口
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
public interface TemplateGroupService {

    /**
     * 列表
     */
    PageResDto<TemplateGroupResDto> select(TemplateGroupQueryReqDto reqDto);

    /**
     * 查询
     */
    TemplateGroupResDto get(TemplateGroupReqDto reqDto);

    TemplateGroupModel getById(Long id);

    /**
     * 添加
     */
    int insert(TemplateGroupReqDto reqDto);

    /**
     * 修改
     */
    int update(TemplateGroupReqDto reqDto);

    /**
     * 删除
     */
    int delete(TemplateGroupReqDto reqDto);

    List<TemplateGroupSelectResDto> listAll();

    List<TemplateGroupModel> listAllModel();
}

