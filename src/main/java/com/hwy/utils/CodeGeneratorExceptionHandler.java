package com.hwy.utils;

import com.alibaba.fastjson.JSON;
import com.hwy.dto.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Component
@Slf4j
public class CodeGeneratorExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ResultData<?> r = new ResultData<>();
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			if (ex instanceof CodeGeneratorException) {
				CodeGeneratorException cge = (CodeGeneratorException) ex;
				r.setCode(String.valueOf(cge.getCode()));
				r.setMessage(cge.getMessage());
			}else if(ex instanceof DuplicateKeyException){
				r = ResultUtil.error("数据库中已存在该记录");
			}else{
				r = ResultUtil.error("");
			}
			//记录异常日志
			log.error(ex.getMessage(), ex);
			String json = JSON.toJSONString(r);
			response.getWriter().print(json);
		} catch (Exception e) {
			log.error("CodeGeneratorExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
	}
}
