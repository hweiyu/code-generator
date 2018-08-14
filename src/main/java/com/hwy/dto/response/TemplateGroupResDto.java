package com.hwy.dto.response;

import com.hwy.model.TemplateGroupModel;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板组返回dto
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateGroupResDto extends BaseRes {

    private static final long serialVersionUID = -5356587689976514472L;

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

    public static TemplateGroupResDto get(TemplateGroupModel model) {
        return TemplateGroupResDto.builder()
                .id(model.getId())
                .groupName(model.getGroupName())
                .moduleName(model.getModuleName())
                .author(model.getAuthor())
                .tablePrefix(model.getTablePrefix())
                .mainPackage(model.getMainPackage())
                .dataStatus(model.getDataStatus())
                .createTime(model.getCreateTime())
                .updateTime(model.getUpdateTime())
                .build();
    }
}
