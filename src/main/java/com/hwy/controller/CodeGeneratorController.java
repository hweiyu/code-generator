package com.hwy.controller;

import com.hwy.dto.ResultData;
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
import java.util.Map;

/**
 * 主程序控制器
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Controller
@RequestMapping("/code/generator")
public class CodeGeneratorController {

	@Autowired
	private CodeGeneratorService codeGeneratorService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResultData<PageResDto<TableResDto>> list(@RequestParam Map<String, Object> params){
		return ResultUtil.success(codeGeneratorService.list(params));
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
