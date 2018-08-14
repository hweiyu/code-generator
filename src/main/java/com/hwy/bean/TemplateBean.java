package com.hwy.bean;

import com.hwy.model.TemplateModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板信息bean
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TemplateBean {

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 模板内容
     */
    private String context;

    /**
     * 1:java,2:xml,3:html,4:javascript
     */
    private Integer templateType;

    /**
     * 1:java,2:xml,3:html,4:javascript
     */
    private String templateTypeName;

    /**
     * 生成包名
     */
    private String packagePath;

    /**
     * 文件名
     */
    private String fileName;

    public static TemplateBean get(TemplateModel model) {
        return TemplateBean.builder()
                .templateName(model.getTemplateName())
                .context(model.getContext())
                .templateType(model.getTemplateType())
                .packagePath(model.getPackagePath())
                .fileName(model.getFileName())
                .build();
    }
}
