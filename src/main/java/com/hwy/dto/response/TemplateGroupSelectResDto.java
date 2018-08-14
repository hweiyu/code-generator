package com.hwy.dto.response;

import com.hwy.model.TemplateGroupModel;
import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板组选择返回dto
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateGroupSelectResDto extends BaseRes {

    private static final long serialVersionUID = 1867275878457507761L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板组名称
     */
    private String groupName;

    public static TemplateGroupSelectResDto get(TemplateGroupModel model) {
        return TemplateGroupSelectResDto.builder()
                .id(model.getId())
                .groupName(model.getGroupName())
                .build();
    }
}
