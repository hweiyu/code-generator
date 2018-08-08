package com.hwy.entity.template;

import java.io.File;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/8 18:38
 **/
public class ListTemp extends BaseTemplate {

    public ListTemp(String template, String packagePath, String moduleName) {
        super(template, packagePath, moduleName);
    }

    @Override
    public String getFileName(String className) {
        return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views"
                + File.separator + "modules" + File.separator + getModuleName() + File.separator
                + className.toLowerCase() + ".vue";
    }
}
