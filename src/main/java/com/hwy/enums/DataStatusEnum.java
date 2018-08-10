package com.hwy.enums;

import com.hwy.utils.CodeGeneratorException;

/**
 * 0:禁用,1:启用,2:已删除
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
public enum DataStatusEnum {

    /**
     * 禁用
     */
    DISABLE(0, "禁用"),

    /**
     * 启用
     */
    ENABLE(1, "启用") ,

    /**
     * 已删除
     */
    DELETED(2, "已删除");

    DataStatusEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static DataStatusEnum typeOf(Integer type) {
        for (DataStatusEnum curEnum : values()) {
            if (null != type && curEnum.getType() == type) {
                return curEnum;
            }
        }
        throw new CodeGeneratorException("DataStatusEnum param error, type:" + type);
    }

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
