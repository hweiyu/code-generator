package com.hwy.controller;

import com.hwy.dto.ResultData;
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

/**
 * 模板控制器
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 列表
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<PageResDto<TemplateResDto>> list(@RequestBody TemplateQueryReqDto reqDto){
        return ResultUtil.success(templateService.select(reqDto));
    }

    /**
     * 查询
     */
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<TemplateResDto> get(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.success(templateService.get(reqDto));
    }

    /**
     * 插入
     */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> insert(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.create(templateService.insert(reqDto));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> update(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.create(templateService.update(reqDto));
    }

    /**
     * 删除
     */
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> delete(@RequestBody TemplateReqDto reqDto){
        return ResultUtil.create(templateService.delete(reqDto));
    }
}
