package com.hwy.service;

import com.hwy.dto.request.TemplateGroupQueryReqDto;
import com.hwy.dto.request.TemplateGroupReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateGroupResDto;
import com.hwy.dto.response.TemplateGroupSelectResDto;
import com.hwy.model.TemplateGroupModel;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板组接口
 * @date 2018/8/13 10:10
 **/
public interface TemplateGroupService {

    /**
     * 列表
     */
    PageResDto<TemplateGroupResDto> select(TemplateGroupQueryReqDto reqDto);

    /**
     * 查询
     */
    TemplateGroupResDto get(TemplateGroupReqDto reqDto);

    /**
     * 通过id查
     * @param id
     * @return
     */
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

    /**
     * 模板组列表
     * @return
     */
    List<TemplateGroupSelectResDto> listAll();

    /**
     * 查所有模板组
     * @return
     */
    List<TemplateGroupModel> listAllModel();
}

