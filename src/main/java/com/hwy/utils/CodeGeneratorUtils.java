package com.hwy.utils;

import com.hwy.entity.ColumnEntity;
import com.hwy.entity.TableEntity;
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

    private static List<String> templates = new ArrayList<>(9);

    static {
        templates.add("template/Entity.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/menu.sql.vm");
        templates.add("template/index.vue.vm");
        templates.add("template/add-or-update.vue.vm");
    }

    private static List<String> getTemplates() {
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(TableModel table,
                                     List<ColumnModel> columns,
                                     ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableEntity tableEntity = createTableEntity(table, config, columns);
        //封装模板数据
        VelocityContext context = createVelocityContext(tableEntity, config);
        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            wrapZip(template, context, tableEntity, config, zip);
        }
    }

    private static void wrapZip(String template, VelocityContext context,
                                TableEntity tableEntity, Configuration config, ZipOutputStream zip) {
        //渲染模板
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, "UTF-8");
        tpl.merge(context, sw);
        try {
            //添加到zip
            String name = getFileName(template, tableEntity.getClassName(), config.getString("package"),
                    config.getString("moduleName"));
            zip.putNextEntry(new ZipEntry(name));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new CodeGeneratorException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
        }
    }

    private static boolean isPrimaryKey(TableEntity table, ColumnModel column) {
        final String primaryKey = "PRI";
        return primaryKey.equalsIgnoreCase(column.getColumnKey())
                && null == table.getPk();
    }

    private static VelocityContext createVelocityContext(
            TableEntity tableEntity,
            Configuration config) {
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> templateData = wrapTemplateData(tableEntity, config);
        return new VelocityContext(templateData);
    }

    private static Map<String, Object> wrapTemplateData(
            TableEntity tableEntity,
            Configuration config) {
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
        templateData.put("email", config.getString("email"));
        templateData.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        String mainPath = config.getString("mainPath");
        templateData.put("mainPath", StringUtils.isBlank(mainPath) ? "com.hwy" : mainPath);
        return templateData;
    }

    private static TableEntity createTableEntity(TableModel model, Configuration config, List<ColumnModel> columns) {
        //表信息
        TableEntity entity = TableEntity.get(model);
        //表名转换成Java类名
        String className = tableToJava(entity.getTableName(), config.getString("tablePrefix"));
        entity.setClassName(className);
        entity.setClassname(StringUtils.uncapitalize(className));
        //封装字段数据
        wrapColumnEntity(entity, config, columns);
        //没主键，则第一个字段为主键
        resetPrimaryKey(entity);
        return entity;
    }

    private static ColumnEntity createColumnEntity(ColumnModel column, Configuration config) {
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

    private static void wrapColumnEntity(TableEntity entity, Configuration config, List<ColumnModel> columns) {
        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (ColumnModel column : columns) {
            ColumnEntity columnEntity = createColumnEntity(column, config);
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
     * 获取文件名
     */
    private static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }
        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }
        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }
        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }
        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        if (template.contains("Dao.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }
        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }
        if (template.contains("index.vue.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + ".vue";
        }
        if (template.contains("add-or-update.vue.vm")) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + "-add-or-update.vue";
        }
        return "";
    }

}
