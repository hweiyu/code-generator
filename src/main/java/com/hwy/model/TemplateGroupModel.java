package com.hwy.model;


import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 模板组model
 * @date 2018/8/13 10:10
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_template_group")
public class TemplateGroupModel {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 模板组名称
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 模块名
     */
    @Column(name = "module_name")
    private String moduleName;

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
     * 主包名
     */
    @Column(name = "main_package")
    private String mainPackage;

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
