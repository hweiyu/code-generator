package com.hwy.handler;

import com.hwy.bean.*;
import com.hwy.exception.CodeGenException;
import com.hwy.utils.AssertUtil;
import com.hwy.utils.CollectionUtil;
import com.hwy.utils.DateUtils;
import com.hwy.utils.VelocityUtil;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 代码生成处理器实现
 * @date 2018/8/13 10:10
 **/
public class CodeGeneratorHandlerImpl implements CodeGeneratorHandler {

    /**
     * 封装的参数
     */
    private CodeGeneratorBean param;

    /**
     * 返回zip
     */
    private ZipOutputStream zip;

    public CodeGeneratorHandlerImpl(CodeGeneratorBean param, ZipOutputStream zip) {
        this.param = param;
        this.zip = zip;
    }

    @Override
    public void generatorCode() {
        for (String tableName : param.getTableNameList()) {
            //生成代码
            generatorCode(tableName);
        }
    }

    /**
     * 配置信息
     */
    private static Configuration config = getConfig();

    /**
     * 获取配置信息
     */
    private static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new CodeGenException("获取配置文件失败，", e);
        }
    }

    /**
     * 生成代码
     */
    private void generatorCode(String tableName) {
        //查询表信息
        TableBean table = param.getTableMap().get(tableName);
        AssertUtil.notNull(table, "表信息为空");
        //查询列信息
        List<ColumnBean> columns = param.getColumnMap().get(tableName);
        AssertUtil.notNull(columns, "表字段信息为空");
        //表信息
        wrapTableBean(table, columns);
        //封装模板数据
        for (TemplateBean template : param.getTemplateList()) {
            VelocityContext context = wrapVelocityContext(table);
            wrapZip(template, context, table, zip);
        }
    }

    private void wrapZip(TemplateBean template, VelocityContext context,
                                TableBean tableBean, ZipOutputStream zip) {
        //渲染模板
        StringWriter sw = VelocityUtil.create(template.getContext(), context);
        try {
            //添加到zip
            String fileName = getFileName(template, tableBean);
            zip.putNextEntry(new ZipEntry(fileName));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new CodeGenException("渲染模板失败，表名：" + tableBean.getTableName(), e);
        }
    }

    private String getFileName(TemplateBean template, TableBean tableBean) {
        return param.getGroup().getMainPackage().replace(".", File.separator)
                + File.separator
                + template.getPackagePath().replace(".", File.separator)
                + File.separator
                + tableBean.getClassName() + template.getFileName();
    }

    private boolean isPrimaryKey(TableBean table, ColumnBean column) {
        final String primaryKey = "PRI";
        return primaryKey.equalsIgnoreCase(column.getColumnKey())
                && null == table.getPk();
    }

    private VelocityContext wrapVelocityContext(TableBean table) {
        //封装模板数据
        Map<String, Object> templateData = wrapTemplateData(table);
        return new VelocityContext(templateData);
    }

    private Map<String, Object> wrapTemplateData(TableBean tableBean) {
        //封装模板数据
        Map<String, Object> templateData = CollectionUtil.newHashMap();
        templateData.put("tableName", tableBean.getTableName());
        templateData.put("comments", tableBean.getComments());
        templateData.put("pk", tableBean.getPk());
        templateData.put("className", tableBean.getClassName());
        templateData.put("classname", tableBean.getClassname());
        templateData.put("pathName", tableBean.getClassname().toLowerCase());
        templateData.put("columns", tableBean.getColumns());
        templateData.put("hasBigDecimal", tableBean.getHasBigDecimal());
        templateData.put("package", param.getGroup().getMainPackage());
        templateData.put("author", param.getGroup().getAuthor());
        templateData.put("moduleName", param.getGroup().getModuleName());
        templateData.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        return templateData;
    }

    private void wrapTableBean(TableBean table, List<ColumnBean> columns) {
        //设置类名
        setClassName(param.getGroup().getTablePrefix(), table);
        //封装字段数据
        wrapColumnBean(table, columns);
        //没主键，则第一个字段为主键
        resetPrimaryKey(table);
    }

    private static void setClassName(String prefix, TableBean entity) {
        //表名转换成Java类名
        String className = tableToJava(entity.getTableName(), prefix);
        entity.setClassName(className);
        entity.setClassname(StringUtils.uncapitalize(className));
    }

    private void wrapColumnBean(ColumnBean column) {
        //列名转换成Java属性名
        String attrName = columnToJava(column.getColumnName());
        column.setAttrName(attrName);
        column.setAttrname(StringUtils.uncapitalize(attrName));
        //列的数据类型，转换成Java类型
        String attrType = config.getString(column.getDataType(), "unknowType");
        column.setAttrType(attrType);
    }

    private void wrapColumnBean(TableBean table, List<ColumnBean> columns) {
        //列信息
        List<ColumnBean> columsList = CollectionUtil.newArrayList();
        for (ColumnBean column : columns) {
            wrapColumnBean(column);
            if (hasBigDecimal(table, column)) {
                table.setHasBigDecimal(Boolean.TRUE);
            }
            //是否主键
            if (isPrimaryKey(table, column)) {
                table.setPk(column);
            }
            columsList.add(column);
        }
        table.setColumns(columsList);
    }

    private static boolean hasBigDecimal(TableBean tableBean, ColumnBean columnBean) {
        final String bigDecimal = "BigDecimal";
        return !tableBean.getHasBigDecimal()
                && bigDecimal.equals(columnBean.getAttrType());
    }

    private static void resetPrimaryKey(TableBean entity) {
        if (entity.getPk() == null) {
            entity.setPk(entity.getColumns().get(0));
        }
    }

    /**
     * 列名转换成Java属性名
     */
    private static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix) && tableName.startsWith(tablePrefix)) {
            tableName = tableName.substring(tablePrefix.length());
        }
        return columnToJava(tableName);
    }
}
