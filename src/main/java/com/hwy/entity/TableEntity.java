package com.hwy.entity;

import com.hwy.model.TableModel;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 *
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
public class TableEntity {

	/**
	 * 表的名称
	 */
	private String tableName;

	/**
	 * 表的备注
	 */
	private String comments;

	/**
	 * 表的主键
	 */
	private ColumnEntity pk;

	/**
	 * 表的列名(不包含主键)
	 */
	private List<ColumnEntity> columns;

	/**
	 * 类名(第一个字母大写)，如：sys_user => SysUser
	 */
	private String className;

	/**
	 * 类名(第一个字母小写)，如：sys_user => sysUser
	 */
	private String classname;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ColumnEntity getPk() {
		return pk;
	}

	public void setPk(ColumnEntity pk) {
		this.pk = pk;
	}

	public List<ColumnEntity> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnEntity> columns) {
		this.columns = columns;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public static TableEntity get(TableModel model) {
		TableEntity entity = new TableEntity();
		entity.setTableName(model.getTableName());
		entity.setComments(model.getTableComment());
		return entity;
	}
}
