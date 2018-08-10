package com.hwy.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author huangweiyu
 * @date 2018/3/20 13:53
 **/
public abstract class LangUtils {

    public static int nvl(Integer i) {
        return i == null ? 0 : i;
    }

    public static int nvl(Integer i, int defaults) {
        return i == null ? defaults : i;
    }

    public static long nvl(Long l) {
        return l == null ? 0L : l;
    }

    public static long nvl(Long l, long defaults) {
        return l == null ? defaults : l;
    }

    public static boolean nvl(Boolean b, boolean defaults) {
        return b == null ? defaults : b;
    }

    public static boolean nvl(Boolean b) {
        return null != b && b;
    }

    public static boolean nvl(String bool, boolean defaults) {
        return bool == null ? defaults : Boolean.parseBoolean(bool);
    }

    public static String nvl(String string, String defaults) {
        return string == null ? defaults : string;
    }

    public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
        List<T> result = Lists.newArrayList();
        if (null != fromList) {
            for (F input : fromList) {
                result.add(function.apply(input));
            }
        }
        return result;
    }

    public static <T> List<T> filter(List<T> fromList, Predicate<? super T> predicate) {
        List<T> result = Lists.newArrayList();
        if (null != fromList) {
            for (T input : fromList) {
                if (predicate.test(input)) {
                    result.add(input);
                }
            }
        }
        return result;
    }

    public static List<Long> longList(List<String> stringList) {
        List<Long> result = Lists.newArrayList();
        if (null != stringList) {
            for (String string : stringList) {
                result.add(Long.valueOf(string));
            }
        }
        return result;
    }

}
