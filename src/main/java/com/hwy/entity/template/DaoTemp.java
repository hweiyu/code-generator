package com.hwy.entity.template;

import java.io.File;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/8 15:54
 **/
public class DaoTemp extends BaseTemplate {

    public DaoTemp(String template, String packagePath) {
        super(template, packagePath, null);
    }

    @Override
    public String getFileName(String className) {
        return getPackagePath() + "dao" + File.separator + className + "Dao.java";
    }
}
