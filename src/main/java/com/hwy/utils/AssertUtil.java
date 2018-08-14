package com.hwy.utils;

import com.hwy.exception.CodeGenException;
import org.springframework.util.Assert;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 断言工具类
 * @date 2018/8/13 10:10
 **/
public class AssertUtil {

    /**
     * 非空判断
     * @param obj
     * @param error
     */
    public static void notNull(Object obj, String error) {
        try {
            Assert.notNull(obj, error);
        } catch (Exception e) {
            throw new CodeGenException(e.getMessage());
        }
    }
}
