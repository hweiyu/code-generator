package com.hwy.dto.request;

import com.alibaba.fastjson.JSON;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/10 19:48
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeGenReqDto extends BaseReq {

    private static final long serialVersionUID = -756987923142930041L;

    private List<String> tableNameList;

    private Long groupId;

    public static CodeGenReqDto get(HttpServletRequest request) {
        String tables = request.getParameter("tables");
        List<String> tableList = JSON.parseArray(tables, String.class);
        String groupId = request.getParameter("groupId");
        return CodeGenReqDto.builder()
                .tableNameList(tableList)
                .groupId(null == groupId ? 0L : Long.valueOf(groupId))
                .build();
    }
}
