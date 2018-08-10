package com.hwy.dto.request;

import com.hwy.model.TemplateModel;
import lombok.*;

/**
 * 模板返回dto
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateReqDto extends BaseReq {

    private static final long serialVersionUID = -3092449831905846232L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 功能模块名称
     */
    private String moduleName;

    /**
     * 模板内容
     */
    private String context;

    /**
     * 作者
     */
    private String author;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 0:java,1:xml,2:html
     */
    private Integer templateType;

    /**
     * 生成包名
     */
    private String packagePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 0:禁用,1:启用,2:已删除
     */
    private Integer dataStatus;

    public TemplateModel to() {
        return TemplateModel.builder()
                .id(getId())
                .templateName(getTemplateName())
                .moduleName(getModuleName())
                .context(getContext())
                .author(getAuthor())
                .tablePrefix(getTablePrefix())
                .templateType(getTemplateType())
                .packagePath(getPackagePath())
                .fileName(getFileName())
                .dataStatus(getDataStatus())
                .build();
    }
}
