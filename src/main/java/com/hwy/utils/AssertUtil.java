package com.hwy.utils;

import com.hwy.exception.CodeGenException;
import org.springframework.util.Assert;

/**
 * @author huangweiyu
 * @version V1.0
 * @date 10:54
 **/
public class AssertUtil {

    public static void notNull(Object obj, String error) {
        try {
            Assert.notNull(obj, error);
        } catch (Exception e) {
            throw new CodeGenException(e.getMessage());
        }
    }
}
