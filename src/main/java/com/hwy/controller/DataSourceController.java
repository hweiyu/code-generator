package com.hwy.controller;

import com.hwy.dto.response.DataSourceSelectResDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.ResultData;
import com.hwy.dto.request.DataSourceQueryReqDto;
import com.hwy.dto.request.DataSourceReqDto;
import com.hwy.dto.response.DataSourceResDto;
import com.hwy.service.DataSourceService;
import com.hwy.utils.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据库源控制器
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    /**
     * 列表
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<PageResDto<DataSourceResDto>> list(@RequestBody DataSourceQueryReqDto reqDto){
        return ResultUtil.success(dataSourceService.select(reqDto));
    }

    /**
     * 查询
     */
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<DataSourceResDto> get(@RequestBody DataSourceReqDto reqDto){
        return ResultUtil.success(dataSourceService.get(reqDto));
    }

    /**
     * 插入
     */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> insert(@RequestBody DataSourceReqDto reqDto){
        return ResultUtil.create(dataSourceService.insert(reqDto));
    }

    /**
     * 修改
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> update(@RequestBody DataSourceReqDto reqDto){
        return ResultUtil.create(dataSourceService.update(reqDto));
    }

    /**
     * 删除
     */
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<Void> delete(@RequestBody DataSourceReqDto reqDto){
        return ResultUtil.create(dataSourceService.delete(reqDto));
    }

    @PostMapping("/connect/test")
    public ResultData<String> connectTest(@RequestBody DataSourceReqDto reqDto){
        boolean res = dataSourceService.connectTest(reqDto);
        return ResultUtil.success(res ? "连接成功" : "连接失败");
    }

    /**
     * 列表
     */
    @PostMapping(value = "/list/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData<List<DataSourceSelectResDto>> listAll(){
        return ResultUtil.success(dataSourceService.listAll());
    }
}
