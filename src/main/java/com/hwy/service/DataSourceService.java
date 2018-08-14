package com.hwy.service;

import com.hwy.dto.response.DataSourceSelectResDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.request.DataSourceQueryReqDto;
import com.hwy.dto.request.DataSourceReqDto;
import com.hwy.dto.response.DataSourceResDto;
import com.hwy.model.DataSourceModel;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 数据源接口
 * @date 2018/8/13 10:10
 **/
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

    /**
     * 连接测试
     * @param reqDto
     */
    void connectTest(DataSourceReqDto reqDto);

    /**
     * 数据源列表
     * @return
     */
    List<DataSourceSelectResDto> listAll();
}

