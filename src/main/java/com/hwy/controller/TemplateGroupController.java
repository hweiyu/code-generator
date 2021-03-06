package com.hwy.controller;

import com.hwy.dto.ResultData;
import com.hwy.dto.request.TemplateGroupQueryReqDto;
import com.hwy.dto.request.TemplateGroupReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TemplateGroupResDto;
import com.hwy.dto.response.TemplateGroupSelectResDto;
import com.hwy.service.TemplateGroupService;

import com.hwy.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板组控制器
 * @date 2018/8/13 10:10
 **/
@RestController
@RequestMapping("group")
public class TemplateGroupController {

    @Autowired
    private TemplateGroupService templateGroupService;

    /**
     * 模板组列表
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<PageResDto<TemplateGroupResDto>> list(@RequestBody TemplateGroupQueryReqDto reqDto){
        return ResultUtil.success(templateGroupService.select(reqDto));
    }

    /**
     * 模板组查询
     */
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<TemplateGroupResDto> get(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.success(templateGroupService.get(reqDto));
    }

    /**
     * 模板组插入
     */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> insert(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.create(templateGroupService.insert(reqDto));
    }

    /**
     * 模板组修改
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> update(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.create(templateGroupService.update(reqDto));
    }

    /**
     * 模板组删除
     */
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> delete(@RequestBody TemplateGroupReqDto reqDto){
        return ResultUtil.create(templateGroupService.delete(reqDto));
    }

    /**
     * 模板组列表
     */
    @PostMapping(value = "/list/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<List<TemplateGroupSelectResDto>> listAll(){
        return ResultUtil.success(templateGroupService.listAll());
    }
}
