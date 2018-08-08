package com.hwy.entity.template;

import java.io.File;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/8 15:54
 **/
public class DaoXmlTemp extends BaseTemplate {

    public DaoXmlTemp(String template, String packagePath, String moduleName) {
        super(template, packagePath, moduleName);
    }

    @Override
    public String getFileName(String name) {
        return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + getModuleName() + File.separator + name + "-mapper.xml";
    }
}
