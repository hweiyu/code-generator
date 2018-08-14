package com.hwy.controller;

import com.hwy.dto.ResultData;
import com.hwy.dto.request.CodeGenQueryReqDto;
import com.hwy.dto.request.CodeGenReqDto;
import com.hwy.dto.response.TableResDto;
import com.hwy.service.CodeGeneratorService;
import com.hwy.dto.response.PageResDto;
import com.hwy.utils.ResultUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 生成代码控制器
 * @date 2018/8/13 10:10
 **/
@Controller
@RequestMapping("/code/generator")
public class CodeGeneratorController {

	@Autowired
	private CodeGeneratorService codeGeneratorService;
	
	/**
	 * 表信息列表
	 */
	@ResponseBody
	@PostMapping("/list")
	public ResultData<PageResDto<TableResDto>> list(@RequestBody CodeGenQueryReqDto reqDto){
		return ResultUtil.success(codeGeneratorService.list(reqDto));
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		byte[] data = codeGeneratorService.generatorCode(CodeGenReqDto.get(request));
		setResponseHeader(data, response);
        IOUtils.write(data, response.getOutputStream());
	}

	private void setResponseHeader(byte[] data, HttpServletResponse response) {
		response.reset();
		response.setHeader("Content-Disposition",
				"attachment; filename=\"code_" + System.currentTimeMillis() + ".zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
	}
}
