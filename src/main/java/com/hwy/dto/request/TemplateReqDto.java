package com.hwy.dto.request;

import com.hwy.model.TemplateModel;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板请求dto
 * @date 2018/8/13 10:10
 **/
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
     * 模板组id
     */
    private Long groupId;

    /**
     * 模板内容
     */
    private String context;

    /**
     * 1:java,2:xml,3:html,4:javascript
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
                .groupId(getGroupId())
                .context(getContext())
                .templateType(getTemplateType())
                .packagePath(getPackagePath())
                .fileName(getFileName())
                .dataStatus(getDataStatus())
                .build();
    }
}
