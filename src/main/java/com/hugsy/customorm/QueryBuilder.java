package com.hugsy.customorm;

import com.hugsy.customorm.annotation.Column;
import com.hugsy.customorm.annotation.Table;

import java.lang.reflect.Field;

public class QueryBuilder {
    public static String createTable(Class<?> c) {
        String query = "create table " + c.getAnnotation(Table.class).name() + "(";
        if (c.getDeclaredFields().length > 0) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    String columnInfo = field.getAnnotation(Column.class).name() + " " +
                            getType(field);
                    query = query + columnInfo+",";

                }
            }
        }
        return query.toUpperCase().substring(0, query.length() - 1) + ")";
    }

    static String getType(Field field) {
        String type;
        if (Long.class.equals(field.getType())) {
            type = "BIGINT";
        } else if (Integer.class.equals(field.getType())) {
            type = "INT";
        } else if (String.class.equals(field.getType())) {
            type = "NVARCHAR(" + field.getAnnotation(Column.class).length() + ")";
        } else if (Boolean.class.equals(field.getType())) {
            type = "BIT";
        } else if (Float.class.equals(field.getType())) {
            type = "REAL";
        } else if (Double.class.equals(field.getType())) {
            type = "FLOAT";
        } else {
            type = "";
        }
        return type;
    }
}
