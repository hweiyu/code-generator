package com.hwy.model;


import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

/**
 * 模板模型
 *
 * @author hweiyu
 * @date 2018-08-10 09:47:28
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_template")
public class TemplateModel {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 模板名
     */
    @Column(name = "template_name")
    private String templateName;

    /**
     * 模板内容
     */
    @Column(name = "context")
    private String context;

    /**
     * 作者
     */
    @Column(name = "author")
    private String author;

    /**
     * 表前缀
     */
    @Column(name = "table_prefix")
    private String tablePrefix;

    /**
     * 0:java,1:xml,2:html
     */
    @Column(name = "template_type")
    private Integer templateType;

    /**
     * 生成包名
     */
    @Column(name = "package_path")
    private String packagePath;

    /**
     * 生成的文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 0:禁用,1:启用,2:已删除
     */
    @Column(name = "data_status")
    private Integer dataStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;
}
