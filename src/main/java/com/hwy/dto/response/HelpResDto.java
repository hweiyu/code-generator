package com.hwy.dto.response;

import com.hwy.model.TemplateGroupModel;
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
public class HelpResDto extends BaseRes {

    private static final long serialVersionUID = -5146343415924425326L;

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
     * 生成包名
     */
    private String packagePath;

    /**
     * 文件名
     */
    private String fileName;

    public static HelpResDto get(TemplateGroupModel group, TemplateModel template) {
        return HelpResDto.builder()
                .moduleName(group.getModuleName())
                .author(group.getAuthor())
                .tablePrefix(group.getTablePrefix())
                .mainPackage(group.getMainPackage())
                .packagePath(template.getPackagePath())
                .fileName(template.getFileName())
                .build();
    }

}
