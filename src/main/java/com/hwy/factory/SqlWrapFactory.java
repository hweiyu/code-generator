package com.hwy.factory;

import com.hwy.anno.SqlParam;
import com.hwy.bean.param.SqlParamBean;
import com.hwy.exception.CodeGenException;
import com.hwy.utils.CollectionUtil;
import com.hwy.utils.LangUtils;
import com.hwy.utils.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 描述
 * @date 2018/8/13 10:57
 **/
@Slf4j
public class SqlWrapFactory {

    private String sql;

    private String whereCondition;

    private String sortCondition;

    private String limitCondition;

    private SqlParamBean param;

    private List<Field> fields;

    public static SqlWrapFactory builder() {
        return new SqlWrapFactory();
    }

    private boolean hasCondition() {
        return null != param;
    }

    public SqlWrapFactory sql(String sql) {
        this.sql = sql;
        return this;
    }

    public SqlWrapFactory condition(SqlParamBean param) {
        this.param = param;
        initFields();
        return this;
    }

    public String build() {
        if (hasCondition()) {
            where();
            sort();
            limit();
        }
        String resultSql = wrapSql();
        log.info("======sql: " + resultSql);
        return resultSql;
    }

    private String wrapSql() {
        return format(sql) + format(whereCondition) + format(sortCondition) + format(limitCondition);
    }

    private String format(String str) {
        return null == str ? "" : str;
    }

    private void initFields() {
        if (hasCondition()) {
            fields = ReflectUtil.getAllField(param.getClass());
        }
    }

    private void where() {
        if (hasCondition()) {
            List<String> where = CollectionUtil.newArrayList();
            for (Field field : fields) {
                SqlParam sqlParam = field.getAnnotation(SqlParam.class);
                if (isWhereCondition(sqlParam)) {
                    Object value = ReflectUtil.getValue(field, param);
                    if (null != value) {
                        if (value instanceof String
                                && "".equals(String.valueOf(value))) {
                            continue;
                        }
                        String cond = wrapSingleCondition(field, sqlParam);
                        where.add(cond);
                    }
                }
            }
            if (CollectionUtil.isNotEmpty(where)) {
                whereCondition = " where " + StringUtils.join(where, " and ");
            }
        }
    }

    private String wrapSingleCondition(Field field, SqlParam sqlParam) {
        String column = isNotEmpty(sqlParam.column()) ? sqlParam.column() : field.getName();
        for (SqlParam.ConditionTypeEnum curEnum : sqlParam.type()) {
            if (SqlParam.ConditionTypeEnum.EQUALS == curEnum) {
                return "(" + column + " = " + getParamValue(field, sqlParam) + ")";
            } else if (SqlParam.ConditionTypeEnum.LIKE == curEnum) {
                return "(" + column + " like '%" + getParamValue(field, sqlParam) + "%')";
            }
        }
        return "";
    }

    private String getParamValue(Field field, SqlParam sqlParam) {
        boolean isLike = false;
        for (SqlParam.ConditionTypeEnum curEnum : sqlParam.type()) {
            if (SqlParam.ConditionTypeEnum.LIKE == curEnum) {
                isLike = true;
            }
        }
        return !isLike && sqlParam.singleQuotes()
                ? "'" + ReflectUtil.getValue(field, param) + "'"
                : String.valueOf(ReflectUtil.getValue(field, param));
    }

    private boolean isNotEmpty(String str) {
        return null != str && !"".equals(str);
    }

    private boolean isWhereCondition(SqlParam sqlParam) {
        if (null == sqlParam) {
            return false;
        }
        for (SqlParam.ConditionTypeEnum curEnum : sqlParam.type()) {
            if (SqlParam.ConditionTypeEnum.EQUALS == curEnum
                    || SqlParam.ConditionTypeEnum.LIKE == curEnum) {
                return true;
            }
        }
        return false;
    }

    private void sort() {
        if (hasCondition()) {
            List<SqlParam> sort = CollectionUtil.newArrayList();
            Map<SqlParam, String> columnMap = CollectionUtil.newHashMap();
            List<SqlParam.ConditionTypeEnum> asc = CollectionUtil.newArrayList();
            List<SqlParam.ConditionTypeEnum> desc = CollectionUtil.newArrayList();
            for (Field field : fields) {
                SqlParam sqlParam = field.getAnnotation(SqlParam.class);
                if (isSortCondition(sqlParam)) {
                    for (SqlParam.ConditionTypeEnum curEnum : sqlParam.type()) {
                        if (SqlParam.ConditionTypeEnum.ASC == curEnum) {
                            asc.add(curEnum);
                        } else if (SqlParam.ConditionTypeEnum.DESC == curEnum) {
                            desc.add(curEnum);
                        }
                    }
                    columnMap.put(sqlParam, isNotEmpty(sqlParam.column()) ? sqlParam.column() : field.getName());
                    sort.add(sqlParam);
                }
            }
            if (CollectionUtil.isNotEmpty(asc) && CollectionUtil.isNotEmpty(desc)) {
                throw new CodeGenException("不能同时存在升序和降序");
            }
            sort.sort(new Comparator<SqlParam>() {
                @Override
                public int compare(SqlParam o1, SqlParam o2) {
                    return o1.sort() - o2.sort();
                }
            });
            if (CollectionUtil.isNotEmpty(sort)) {
                StringBuilder buf = new StringBuilder(256);
                for (SqlParam sqlParam : sort) {
                    buf.append(",").append(columnMap.get(sqlParam));
                }
                sortCondition = " order by " + buf.substring(1) +
                        (CollectionUtil.isNotEmpty(asc) ? " asc" : CollectionUtil.isNotEmpty(desc) ? " desc" : "");
            }
        }
    }

    private boolean isSortCondition(SqlParam sqlParam) {
        if (null == sqlParam) {
            return false;
        }
        for (SqlParam.ConditionTypeEnum curEnum : sqlParam.type()) {
            if (SqlParam.ConditionTypeEnum.ASC == curEnum
                    || SqlParam.ConditionTypeEnum.DESC == curEnum) {
                return true;
            }
        }
        return false;
    }

    private boolean isSortCondition(SqlParam.ConditionTypeEnum curEnum) {
        return SqlParam.ConditionTypeEnum.ASC == curEnum
                || SqlParam.ConditionTypeEnum.DESC == curEnum;
    }

    public void limit() {
        if (hasLimit()) {
            int offset = LangUtils.nvl(param.getOffset(), 0);
            int limit = LangUtils.nvl(param.getLimit(), 0);
            limitCondition = " limit " + offset + ((limit > 0) ? (", " + limit) : "");
        }
    }

    private boolean hasLimit() {
        return null != param.getOffset() && null != param.getLimit();
    }
}
