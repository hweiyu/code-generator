package com.hwy.utils;

import com.hwy.dto.CodeResult;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/6/14 9:26
 **/
public class ResultUtil {

    private static final String TRUE = Boolean.TRUE.toString();

    private static final String FLASE = Boolean.FALSE.toString();

    private static final String DEFAULT_CODE = "1";

    public static<T> CodeResult<T> success(T data) {
        CodeResult<T> result = new CodeResult<>();
        result.setResult(TRUE);
        result.setData(data);
        result.setCode(DEFAULT_CODE);
        return result;
    }

    public static <T> CodeResult<T> error(String message) {
        return error(message, null);
    }

    public static <T> CodeResult<T> error(String message, T data) {
        CodeResult<T> result = new CodeResult<>();
        result.setResult(FLASE);
        result.setMessage(message);
        result.setData(data);
        result.setCode(DEFAULT_CODE);
        return result;
    }

    public static <T> CodeResult<T> create(boolean res, String message, T data) {
        return res ? success(data) : error(message, data);
    }
}
