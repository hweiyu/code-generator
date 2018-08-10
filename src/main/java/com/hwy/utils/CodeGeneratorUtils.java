package com.hwy.utils;

import com.hwy.entity.ColumnEntity;
import com.hwy.entity.TableEntity;
import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import com.hwy.model.TemplateModel;
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
 * 代码生成器工具类
 *
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
public class CodeGeneratorUtils {

    private CodeGeneratorUtils() {
        throw new AssertionError("not support to new a instance");
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
            throw new CodeGeneratorException("获取配置文件失败，", e);
        }
    }

    /**
     * 生成代码
     */
    public static void generatorCode(TableModel table,
                                     List<ColumnModel> columns,
                                     List<TemplateModel> templates,
                                     ZipOutputStream zip) {
        //表信息
        TableEntity tableEntity = createTableEntity(table, columns);
        boolean hasSetting = false;
        //封装模板数据
        for (TemplateModel template : templates) {
            if (!hasSetting) {
//                setClassName(template.getTablePrefix(), tableEntity);
                hasSetting = true;
            }
            VelocityContext context = createVelocityContext(template, tableEntity);
            wrapZip(template, context, tableEntity, zip);
        }
    }

    private static void wrapZip(TemplateModel template, VelocityContext context,
                                TableEntity tableEntity, ZipOutputStream zip) {
        //渲染模板
        StringWriter sw = VelocityUtil.create(template.getContext(), context);
        try {
            //添加到zip
            String fileName = getFileName(template, tableEntity);
            zip.putNextEntry(new ZipEntry(fileName));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new CodeGeneratorException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
        }
    }

    private static String getFileName(TemplateModel template, TableEntity tableEntity) {
        return template.getPackagePath().replace(".", File.separator)
                + File.separator
                + template.getFileName().replace("${name}", tableEntity.getClassName());
    }

    private static boolean isPrimaryKey(TableEntity table, ColumnModel column) {
        final String primaryKey = "PRI";
        return primaryKey.equalsIgnoreCase(column.getColumnKey())
                && null == table.getPk();
    }

    private static VelocityContext createVelocityContext(TemplateModel template, TableEntity tableEntity) {
        //封装模板数据
        Map<String, Object> templateData = wrapTemplateData(template, tableEntity);
        return new VelocityContext(templateData);
    }

    private static Map<String, Object> wrapTemplateData(TemplateModel template, TableEntity tableEntity) {
        //封装模板数据
        Map<String, Object> templateData = new HashMap<>(16);
        templateData.put("tableName", tableEntity.getTableName());
        templateData.put("comments", tableEntity.getComments());
        templateData.put("pk", tableEntity.getPk());
        templateData.put("className", tableEntity.getClassName());
        templateData.put("classname", tableEntity.getClassname());
        templateData.put("pathName", tableEntity.getClassname().toLowerCase());
        templateData.put("columns", tableEntity.getColumns());
        templateData.put("hasBigDecimal", tableEntity.getHasBigDecimal());
        templateData.put("package", template.getPackagePath());
//        templateData.put("author", template.getAuthor());
//        templateData.put("moduleName", template.getModuleName());
        templateData.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        return templateData;
    }

    private static TableEntity createTableEntity(TableModel model, List<ColumnModel> columns) {
        //表信息
        TableEntity entity = TableEntity.get(model);
        //封装字段数据
        wrapColumnEntity(entity, columns);
        //没主键，则第一个字段为主键
        resetPrimaryKey(entity);
        return entity;
    }

    private static void setClassName(String prefix, TableEntity entity) {
        //表名转换成Java类名
        String className = tableToJava(entity.getTableName(), prefix);
        entity.setClassName(className);
        entity.setClassname(StringUtils.uncapitalize(className));
    }

    private static ColumnEntity createColumnEntity(ColumnModel column) {
        ColumnEntity entity = ColumnEntity.get(column);
        //列名转换成Java属性名
        String attrName = columnToJava(entity.getColumnName());
        entity.setAttrName(attrName);
        entity.setAttrname(StringUtils.uncapitalize(attrName));
        //列的数据类型，转换成Java类型
        String attrType = config.getString(entity.getDataType(), "unknowType");
        entity.setAttrType(attrType);
        return entity;
    }

    private static void wrapColumnEntity(TableEntity entity, List<ColumnModel> columns) {
        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (ColumnModel column : columns) {
            ColumnEntity columnEntity = createColumnEntity(column);
            if (hasBigDecimal(entity, columnEntity)) {
                entity.setHasBigDecimal(Boolean.TRUE);
            }
            //是否主键
            if (isPrimaryKey(entity, column)) {
                entity.setPk(columnEntity);
            }
            columsList.add(columnEntity);
        }
        entity.setColumns(columsList);
    }

    private static boolean hasBigDecimal(TableEntity tableEntity, ColumnEntity columnEntity) {
        final String bigDecimal = "BigDecimal";
        return !tableEntity.getHasBigDecimal()
                && bigDecimal.equals(columnEntity.getAttrType());
    }

    private static void resetPrimaryKey(TableEntity entity) {
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
