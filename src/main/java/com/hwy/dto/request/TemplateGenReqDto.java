package com.hwy.dto.request;

import com.hwy.model.TemplateModel;
import lombok.*;

import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板生成代码请求dto
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateGenReqDto extends BaseReq {

    private static final long serialVersionUID = -5307556832494744256L;

    /**
     * 模板组id
     */
    private Long groupId;

    /**
     * 表名列表
     */
    private List<String> tableNameList;

}
