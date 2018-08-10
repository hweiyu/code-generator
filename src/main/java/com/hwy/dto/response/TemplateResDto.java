package com.hwy.dto.response;

import com.hwy.enums.TemplateTypeEnum;
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
     * 模板组id
     */
    private Long groupId;

    /**
     * 模板组名称
     */
    private String groupName;

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
                .groupId(model.getGroupId())
                .context(model.getContext())
                .templateType(model.getTemplateType())
                .templateTypeName(TemplateTypeEnum.typeOf(model.getTemplateType()).getDesc())
                .packagePath(model.getPackagePath())
                .fileName(model.getFileName())
                .dataStatus(model.getDataStatus())
                .createTime(model.getCreateTime())
                .updateTime(model.getUpdateTime())
                .build();
    }

    public TemplateResDto setTemplateGroupName(String groupName) {
        setGroupName(groupName);
        return this;
    }
}
