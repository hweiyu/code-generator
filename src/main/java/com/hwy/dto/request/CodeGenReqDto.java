package com.hwy.dto.request;

import com.alibaba.fastjson.JSON;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 生成代码请求dto
 * @date 2018/8/10 19:48
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeGenReqDto extends BaseReq {

    private static final long serialVersionUID = -756987923142930041L;

    /**
     * 表名列表
     */
    private List<String> tableNameList;

    /**
     * 模板组id
     */
    private Long groupId;

    /**
     * 数据源id
     */
    private Long sourceId;

    public static CodeGenReqDto get(HttpServletRequest request) {
        String tables = request.getParameter("tables");
        List<String> tableList = JSON.parseArray(tables, String.class);
        String groupId = request.getParameter("groupId");
        String sourceId = request.getParameter("sourceId");
        return CodeGenReqDto.builder()
                .tableNameList(tableList)
                .groupId(null == groupId ? 0L : Long.valueOf(groupId))
                .sourceId(null == sourceId ? 0L : Long.valueOf(sourceId))
                .build();
    }
}
