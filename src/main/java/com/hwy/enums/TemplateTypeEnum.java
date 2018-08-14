package com.hwy.enums;

import com.hwy.exception.CodeGenException;

/**
 * 模板类型 1:java,2:xml,3:html,4:javascript
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
public enum TemplateTypeEnum {

    /**
     * java
     */
    JAVA(1, "java"),

    /**
     * xml
     */
    XML(2, "xml") ,

    /**
     * html
     */
    HTML(3, "html"),

    /**
     * javascript
     */
    JAVASCRIPT(4, "javascript");

    TemplateTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static TemplateTypeEnum typeOf(Integer type) {
        for (TemplateTypeEnum curEnum : values()) {
            if (null != type && curEnum.getType() == type) {
                return curEnum;
            }
        }
        throw new CodeGenException("TemplateTypeEnum param error, type:" + type);
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
