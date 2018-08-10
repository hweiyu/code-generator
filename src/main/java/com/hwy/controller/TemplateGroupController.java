package com.hwy.controller;

import com.hwy.dto.ResultData;
import com.hwy.dto.request.TemplateGroupQueryReqDto;
import com.hwy.dto.request.TemplateGroupReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateGroupResDto;
import com.hwy.service.TemplateGroupService;

import com.hwy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 模板组控制器
 *
 * @author hweiyu
 * @date 2018-08-10 22:32:56
 */
@RestController
@RequestMapping("group")
public class TemplateGroupController {

    @Autowired
    private TemplateGroupService templateGroupService;

    /**
     * 列表
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<PageResDto<TemplateGroupResDto>> list(@RequestBody TemplateGroupQueryReqDto reqDto){
        return ResultUtil.success(templateGroupService.select(reqDto));
    }

    /**
     * 查询
     */
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<TemplateGroupResDto> get(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.success(templateGroupService.get(reqDto));
    }

    /**
     * 插入
     */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> insert(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.create(templateGroupService.insert(reqDto));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> update(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.create(templateGroupService.update(reqDto));
    }

    /**
     * 删除
     */
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> delete(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.create(templateGroupService.delete(reqDto));
    }
}
