package com.hwy.bean;

import com.hwy.model.TemplateGroupModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @date 12:13
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TemplateGroupBean {

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板组名称
     */
    private String groupName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 作者
     */
    private String author;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 主包名
     */
    private String mainPackage;

    public static TemplateGroupBean get(TemplateGroupModel model) {
        return TemplateGroupBean.builder()
                .id(model.getId())
                .groupName(model.getGroupName())
                .moduleName(model.getModuleName())
                .author(model.getAuthor())
                .tablePrefix(model.getTablePrefix())
                .mainPackage(model.getMainPackage())
                .build();
    }
}
