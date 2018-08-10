package com.hwy.dto.response;

import com.hwy.model.TemplateGroupModel;
import lombok.*;

/**
 * 模板组返回dto
 *
 * @author hweiyu
 * @date 2018-08-10 22:32:56
 */
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
