package com.hwy.utils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Properties;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: velocity工具类
 * @date 2018/8/10 19:23
 **/
public class VelocityUtil {

    static {
        Properties property = new Properties();
        property.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        property.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        Velocity.init(property);
    }

    public static StringWriter create(String template, VelocityContext context) {
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "dynamicTemplate", template);
        return writer;
    }
}
