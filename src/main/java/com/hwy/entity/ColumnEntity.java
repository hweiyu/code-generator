package com.hwy.entity;

import com.hwy.model.ColumnModel;

/**
 *
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
public class ColumnEntity {

	/**
	 * 列名
	 */
    private String columnName;

	/**
	 * 列名类型
	 */
	private String dataType;

	/**
	 * 列名备注
	 */
	private String comments;

	/**
	 * 属性名称(第一个字母大写)，如：user_name => UserName
	 */
	private String attrName;

	/**
	 * 属性名称(第一个字母小写)，如：user_name => userName
	 */
	private String attrname;

	/**
	 * 属性类型
	 */
	private String attrType;

	/**
	 * auto_increment
	 */
	private String extra;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public static ColumnEntity get(ColumnModel model) {
		ColumnEntity entity = new ColumnEntity();
		entity.setColumnName(model.getColumnName());
		entity.setDataType(model.getDataType());
		entity.setComments(model.getColumnComment());
		entity.setExtra(model.getExtra());
		return entity;
	}
}
