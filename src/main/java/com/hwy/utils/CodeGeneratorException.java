package com.hwy.utils;

/**
 * 自定义异常
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
public class CodeGeneratorException extends RuntimeException {

	private static final long serialVersionUID = -9177088994731269290L;

	private int code = 500;
    
    public CodeGeneratorException(String msg) {
		super(msg);
	}
	
	public CodeGeneratorException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public CodeGeneratorException(String msg, int code) {
		super(msg);
		this.code = code;
	}
	
	public CodeGeneratorException(String msg, int code, Throwable e) {
		super(msg, e);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
