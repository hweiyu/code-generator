package com.hwy.dto.request;

import com.hwy.model.TemplateGroupModel;
import lombok.*;

/**
 * 模板组请求dto
 *
 * @author hweiyu
 * @date 2018-08-10 22:32:56
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateGroupReqDto extends BaseReq {

    private static final long serialVersionUID = -5120488001853767439L;

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

    public TemplateGroupModel to() {
        return TemplateGroupModel.builder()
                .id(getId())
                .groupName(getGroupName())
                .moduleName(getModuleName())
                .author(getAuthor())
                .tablePrefix(getTablePrefix())
                .mainPackage(getMainPackage())
                .dataStatus(getDataStatus())
                .createTime(getCreateTime())
                .updateTime(getUpdateTime())
                .build();
    }
}
