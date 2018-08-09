package com.hwy.service;

import com.hwy.dto.PageInfo;
import com.hwy.dto.request.DataSoureReqDto;
import com.hwy.dto.response.DataSoureResDto;
import com.hwy.dto.response.TableResDto;

import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/7 14:51
 **/
public interface SysGeneratorService {

    PageInfo<TableResDto> list(Map<String, Object> map);

    byte[] generatorCode(String[] tableNames);

    DataSoureResDto getCacheConfig();

    void saveCacheConfig(DataSoureReqDto reqDto);

    boolean connectTest(DataSoureReqDto reqDto);
}
