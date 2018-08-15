package com.hwy.dto.response;

import com.hwy.model.TemplateModel;
import lombok.*;
import org.apache.commons.lang.WordUtils;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板生成代码返回dto
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateGenResDto extends BaseRes {

    private static final long serialVersionUID = -4181136818344688260L;

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 模板组名称
     */
    private String groupName;

    /**
     * 生成包名
     */
    private String packagePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表前缀
     */
    private String tablePrefix;

    public static TemplateGenResDto get(TemplateModel model) {
        return TemplateGenResDto.builder()
                .templateName(model.getTemplateName())
                .packagePath(model.getPackagePath())
                .fileName(model.getFileName())
                .build();
    }

    public TemplateGenResDto setTemplateGroupName(String groupName) {
        setGroupName(groupName);
        return this;
    }

    public TemplateGenResDto setFilePath(String mainPath, String moduleName) {
        setPackagePath(mainPath + "." + getPackagePath().replace("${moduleName}", moduleName));
        return this;
    }

    public TemplateGenResDto setFileRealName() {
        if (null != getTableName() && null != getTablePrefix()) {
            String tempTableName = getTableName().substring(getTablePrefix().length());
            String className = WordUtils.capitalizeFully(tempTableName, new char[]{'_'}).replace("_", "");
            setFileName(getFileName().replace("${className}", className));
        }
        return this;
    }

}
