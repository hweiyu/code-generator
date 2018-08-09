package com.hwy.controller;

import com.alibaba.fastjson.JSON;
import com.hwy.dto.ResultData;
import com.hwy.dto.request.DataSoureReqDto;
import com.hwy.dto.response.DataSoureResDto;
import com.hwy.service.SysGeneratorService;
import com.hwy.dto.PageInfo;
import com.hwy.utils.ResultUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 主程序控制器
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {

	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResultData<PageInfo> list(@RequestParam Map<String, Object> params){
		return ResultUtil.success(sysGeneratorService.list(params));
	}

	@ResponseBody
	@PostMapping("/config/get")
	public ResultData<DataSoureResDto> getConfig(){
		return ResultUtil.success(sysGeneratorService.getCacheConfig());
	}

	@ResponseBody
	@PostMapping("/config/save")
	public ResultData<Void> saveConfig(@RequestBody DataSoureReqDto reqDto){
		sysGeneratorService.saveCacheConfig(reqDto);
		return ResultUtil.success(null);
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = getTables(request);
		byte[] data = sysGeneratorService.generatorCode(tableNames);
		setResponseHeader(data, response);
        IOUtils.write(data, response.getOutputStream());
	}

	private String[] getTables(HttpServletRequest request) {
		String tables = request.getParameter("tables");
		List<String> tableList = JSON.parseArray(tables, String.class);
		return tableList.toArray(new String[]{});
	}

	private void setResponseHeader(byte[] data, HttpServletResponse response) {
		response.reset();
		response.setHeader("Content-Disposition",
				"attachment; filename=\"code_" + System.currentTimeMillis() + ".zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
	}
}
