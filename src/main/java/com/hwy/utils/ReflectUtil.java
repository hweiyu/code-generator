package com.hwy.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 反射工具类
 * @date 2018/8/13 11:33
 **/
@Slf4j
public class ReflectUtil {

    public static List<Field> getAllField(Class<?> clazz) {
        List<Field> result = CollectionUtil.newArrayList();
        while (null != clazz) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                result.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    public static Object getValue(Field field, Object obj) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("反射获取值异常", e);
        }
        return null;
    }
}
