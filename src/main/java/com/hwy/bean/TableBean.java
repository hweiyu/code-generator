package com.hwy.bean;

import com.hwy.model.TableModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@Builder
@AllArgsConstructor
public class TableBean {

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
	private ColumnBean pk;

	/**
	 * 表的列名(不包含主键)
	 */
	private List<ColumnBean> columns;

	/**
	 * 类名(第一个字母大写)，如：sys_user => SysUser
	 */
	private String className;

	/**
	 * 类名(第一个字母小写)，如：sys_user => sysUser
	 */
	private String classname;

	/**
	 * 是否存在bigDecimal类型的字段
	 */
	private Boolean hasBigDecimal;

	public static TableBean get(TableModel model) {
		return TableBean.builder()
				.tableName(model.getTableName())
				.comments(model.getTableComment())
				.hasBigDecimal(Boolean.FALSE)
				.build();
	}

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

	public ColumnBean getPk() {
		return pk;
	}

	public void setPk(ColumnBean pk) {
		this.pk = pk;
	}

	public List<ColumnBean> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnBean> columns) {
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

	public Boolean getHasBigDecimal() {
		return hasBigDecimal;
	}

	public void setHasBigDecimal(Boolean hasBigDecimal) {
		this.hasBigDecimal = hasBigDecimal;
	}
}
