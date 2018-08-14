package com.hwy.service;

import com.hwy.dto.request.CodeGenQueryReqDto;
import com.hwy.dto.request.CodeGenReqDto;
import com.hwy.dto.response.PageResDto;
import com.hwy.dto.response.TableResDto;

import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 代码生成接口
 * @date 2018/8/7 14:51
 **/
public interface CodeGeneratorService {

    PageResDto<TableResDto> list(CodeGenQueryReqDto reqDto);

    byte[] generatorCode(CodeGenReqDto reqDto);

}
