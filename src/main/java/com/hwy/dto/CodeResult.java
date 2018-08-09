package com.hwy.dto;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/6/14 9:25
 **/
public class CodeResult<T> extends BaseResult {

    private static final long serialVersionUID = -495303608504114277L;

    private T data;

    public CodeResult() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
