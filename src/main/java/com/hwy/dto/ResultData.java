package com.hwy.dto;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 返回封装数据
 * @date 2018/6/14 9:25
 **/
public class ResultData<T> extends BaseResult {

    private static final long serialVersionUID = -495303608504114277L;

    /**
     * 数据
     */
    private T data;

    public ResultData() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
