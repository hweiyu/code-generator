package com.hwy.bean;

import com.hwy.model.ColumnModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 表字段结构信息bean
 * @date 2018/8/13 10:10
 **/
@Builder
@AllArgsConstructor
public class ColumnBean {

	/**
	 * key
	 */
	private String columnKey;

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

	public static ColumnBean get(ColumnModel model) {
		return ColumnBean.builder()
				.columnKey(model.getColumnKey())
				.columnName(model.getColumnName())
				.dataType(model.getDataType())
				.comments(model.getColumnComment())
				.extra(model.getExtra())
				.build();
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

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
}
