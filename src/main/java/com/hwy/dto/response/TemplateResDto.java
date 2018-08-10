package com.hwy.dto.response;

import com.hwy.model.TemplateModel;

import lombok.*;

/**
 * 模板请求dto
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResDto extends BaseRes {

    private static final long serialVersionUID = -7027004617386271435L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 模板内容
     */
    private String context;

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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    public static TemplateResDto get(TemplateModel model) {
        return TemplateResDto.builder()
                .id(model.getId())
                .templateName(model.getTemplateName())
                .context(model.getContext())
                .templateType(model.getTemplateType())
                .packagePath(model.getPackagePath())
                .fileName(model.getFileName())
                .dataStatus(model.getDataStatus())
                .createTime(model.getCreateTime())
                .updateTime(model.getUpdateTime())
                .build();
    }
}
