package com.hwy.utils;

import java.util.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 集合工具类
 * @date 2018/7/3 17:39
 **/
public class CollectionUtil {

    private static final int LIST_DEAULT_CAPACITY = 10;

    private static final int MAP_DEAULT_CAPACITY = 16;

    private static final int SET_DEAULT_CAPACITY = 16;

    private static final int EMPTY_CAPACITY = 0;

    public static <T> boolean isEmpty(Collection<T> list) {
        return null == list || list.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> list) {
        return !isEmpty(list);
    }

    public static <T> List<T> emptyList() {
        return new ArrayList<>(EMPTY_CAPACITY);
    }

    public static <T> List<T> newArrayList(List<T> list) {
        return null == list ? emptyList() : list;
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>(LIST_DEAULT_CAPACITY);
    }

    public static <T> List<T> newArrayList(int cap) {
        return new ArrayList<>(cap);
    }

    public static <T> Set<T> newHashSet() {
        return new HashSet<>(SET_DEAULT_CAPACITY);
    }

    public static <T> Set<T> newHashSet(int cap) {
        return new HashSet<>(cap);
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>(MAP_DEAULT_CAPACITY);
    }

    public static <T> T randomOne(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        int index = (int) (Math.random() * list.size());
        return list.get(index);
    }

    public static <T> T getOneIfHas(List<T> list) {
        return isEmpty(list) ? null : list.get(0);
    }
}
