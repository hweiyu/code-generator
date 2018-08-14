package com.hwy.dto;

import java.io.Serializable;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 基础返回类
 * @date 2018/6/14 9:21
 **/
public class BaseResult implements Result, Serializable {

    private static final long serialVersionUID = 8796305494299482234L;

    /**
     * 返回结果true/false
     */
    private String result;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    public void setResult(String result) {
        this.result = result;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
