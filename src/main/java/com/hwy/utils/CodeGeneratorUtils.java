package com.hwy.utils;

import com.hwy.entity.ColumnEntity;
import com.hwy.entity.TableEntity;
import com.hwy.entity.template.*;
import com.hwy.model.ColumnModel;
import com.hwy.model.TableModel;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

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
     * 模版信息
     */
    private static List<BaseTemplate> templates = new ArrayList<>(20);

    static {
        String packageName = config.getString("package");
        String moduleName = config.getString("moduleName");
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        templates.add(new ModelTemp("template/Model.java.vm", packagePath));
        templates.add(new ReqDtoTemp("template/ReqDto.java.vm", packagePath));
        templates.add(new ResDtoTemp("template/ResDto.java.vm", packagePath));
        templates.add(new DaoTemp("template/Mapper.java.vm", packagePath));
        templates.add(new DaoXmlTemp("template/Mapper.xml.vm", packagePath, moduleName));
        templates.add(new ServiceTemp("template/Service.java.vm", packagePath));
        templates.add(new ServiceImplTemp("template/ServiceImpl.java.vm", packagePath));
        templates.add(new ControllerTemp("template/Controller.java.vm", packagePath));
        templates.add(new ListTemp("template/List.vue.vm", packagePath, moduleName));
    }

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
                                     ZipOutputStream zip) {
        //表信息
        TableEntity tableEntity = createTableEntity(table, columns);
        //封装模板数据
        VelocityContext context = createVelocityContext(tableEntity);
        //获取模板列表
        for (BaseTemplate template : templates) {
            wrapZip(template, context, tableEntity, zip);
        }
    }

    private static void wrapZip(BaseTemplate template, VelocityContext context,
                                TableEntity tableEntity, ZipOutputStream zip) {
        //渲染模板
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template.getTemplate(), "UTF-8");
        tpl.merge(context, sw);
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

    private static String getFileName(BaseTemplate template, TableEntity tableEntity) {
        String fileName;
        final String defaultMapperXml = "Mapper.xml.vm";
        boolean isMapperXml = template.getTemplate().contains(defaultMapperXml);
        if (isMapperXml) {
            String tablePrefix = config.getString("tablePrefix");
            String name = tableToXml(tableEntity.getTableName(), tablePrefix);
            fileName = template.getFileName(name);
        } else {
            fileName = template.getFileName(tableEntity.getClassName());
        }
        return fileName;
    }

    private static boolean isPrimaryKey(TableEntity table, ColumnModel column) {
        final String primaryKey = "PRI";
        return primaryKey.equalsIgnoreCase(column.getColumnKey())
                && null == table.getPk();
    }

    private static VelocityContext createVelocityContext(TableEntity tableEntity) {
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> templateData = wrapTemplateData(tableEntity);
        return new VelocityContext(templateData);
    }

    private static Map<String, Object> wrapTemplateData(TableEntity tableEntity) {
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
        templateData.put("package", config.getString("package"));
        templateData.put("moduleName", config.getString("moduleName"));
        templateData.put("author", config.getString("author"));
        templateData.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        String mainPath = config.getString("mainPath");
        templateData.put("mainPath", StringUtils.isBlank(mainPath) ? "com.hwy" : mainPath);
        return templateData;
    }

    private static TableEntity createTableEntity(TableModel model, List<ColumnModel> columns) {
        //表信息
        TableEntity entity = TableEntity.get(model);
        //表名转换成Java类名
        String className = tableToJava(entity.getTableName(), config.getString("tablePrefix"));
        entity.setClassName(className);
        entity.setClassname(StringUtils.uncapitalize(className));
        //封装字段数据
        wrapColumnEntity(entity, columns);
        //没主键，则第一个字段为主键
        resetPrimaryKey(entity);
        return entity;
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

    /**
     * 表名转换成xml名
     */
    private static String tableToXml(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix) && tableName.startsWith(tablePrefix)) {
            tableName = tableName.substring(tablePrefix.length());
        }
        return tableName.replace("_", "-");
    }

}
