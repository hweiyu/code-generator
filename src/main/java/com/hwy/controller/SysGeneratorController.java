package com.hwy.controller;

import com.alibaba.fastjson.JSON;
import com.hwy.model.TableModel;
import com.hwy.service.SysGeneratorService;
import com.hwy.utils.Query;
import com.hwy.utils.Result;
import com.hwy.utils.PageUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<TableModel> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return Result.ok().put("page", pageUtil);
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
