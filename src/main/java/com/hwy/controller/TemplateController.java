package com.hwy.controller;

import com.hwy.dto.ResultData;
import com.hwy.dto.request.IdReqDto;
import com.hwy.dto.request.TemplateQueryReqDto;
import com.hwy.dto.request.TemplateReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateResDto;
import com.hwy.service.TemplateService;
import com.hwy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板控制器
 * @date 2018/8/13 10:10
 **/
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 模板列表
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<PageResDto<TemplateResDto>> list(@RequestBody TemplateQueryReqDto reqDto){
        return ResultUtil.success(templateService.select(reqDto));
    }

    /**
     * 模板查询
     */
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<TemplateResDto> get(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.success(templateService.get(reqDto));
    }

    /**
     * 模板插入
     */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> insert(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.create(templateService.insert(reqDto));
    }

    /**
     * 模板修改
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> update(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.create(templateService.update(reqDto));
    }

    /**
     * 模板删除
     */
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> delete(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.create(templateService.delete(reqDto));
    }

    /**
     * 模板列表
     */
    @PostMapping(value = "gen/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<List<TemplateResDto>> genList(@RequestBody IdReqDto reqDto){
        return ResultUtil.success(templateService.genList(reqDto.getId()));
    }

}
