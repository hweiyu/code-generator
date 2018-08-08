package com.hwy.entity.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/8 15:49
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseTemplate {

    private String template;

    private String packagePath;

    private String moduleName;

    public String getFileName(String className) {
        return null;
    }
}
