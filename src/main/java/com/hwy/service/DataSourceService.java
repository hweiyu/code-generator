package com.hwy.service;

import com.hwy.dto.response.DataSourceSelectResDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.request.DataSourceQueryReqDto;
import com.hwy.dto.request.DataSourceReqDto;
import com.hwy.dto.response.DataSourceResDto;
import com.hwy.model.DataSourceModel;

import java.util.List;

/**
 * 数据库源接口
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
public interface DataSourceService {

    /**
    * 列表
    */
    PageResDto<DataSourceResDto> select(DataSourceQueryReqDto reqDto);

    /**
    * 查询
    */
    DataSourceResDto get(DataSourceReqDto reqDto);

    DataSourceModel getById(Long id);

    /**
    * 添加
    */
    int insert(DataSourceReqDto reqDto);

    /**
    * 修改
    */
    int update(DataSourceReqDto reqDto);

    /**
    * 删除
    */
    int delete(DataSourceReqDto reqDto);

    void connectTest(DataSourceReqDto reqDto);

    List<DataSourceSelectResDto> listAll();
}

