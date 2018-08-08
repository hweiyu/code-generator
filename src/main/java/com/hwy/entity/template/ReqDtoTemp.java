package com.hwy.entity.template;

import java.io.File;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/8 15:54
 **/
public class ReqDtoTemp extends BaseTemplate {

    public ReqDtoTemp(String template, String packagePath) {
        super(template, packagePath, null);
    }

    @Override
    public String getFileName(String className) {
        return getPackagePath() + "dto" + File.separator + "request" + File.separator + className + "ReqDto.java";
    }
}
