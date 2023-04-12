package com.hugsy.customorm;

import com.hugsy.customorm.annotation.Column;
import com.hugsy.customorm.annotation.Id;
import com.hugsy.customorm.annotation.ManyToOne;
import com.hugsy.customorm.annotation.Table;

import java.lang.reflect.Field;

public class QueryBuilder {
    public static String createTable(Class<?> c) {
        String query = "create table " + c.getAnnotation(Table.class).name() + "(";
        if (c.getDeclaredFields().length > 0) {
            for (Field field : c.getDeclaredFields()) {
                String columnInfo = "";
                if (field.isAnnotationPresent(Id.class)) {
                    columnInfo = field.getAnnotation(Column.class).name() + " " +
                            getType(field) + (field.getAnnotation(Column.class).nullable() ? "" : " NOT NULL ") +
                            "PRIMARY KEY ";
                } else if (field.isAnnotationPresent(Column.class) &&
                        !field.isAnnotationPresent(ManyToOne.class) &&
                        !field.isAnnotationPresent(Id.class)) {
                    columnInfo = field.getAnnotation(Column.class).name() + " " + getType(field) +
                            (field.getAnnotation(Column.class).nullable() ? "" : " NOT NULL ") +
                            (field.getAnnotation(Column.class).unique() ? " UNIQUE " : "");
                } else if (field.isAnnotationPresent(ManyToOne.class)) {
                    columnInfo = field.getAnnotation(Column.class).name() + " " + getType(field) +
                            (field.getAnnotation(Column.class).nullable() ? "" : " NOT NULL ") +
                            (field.getAnnotation(Column.class).unique() ? " UNIQUE " : "") + " FOREIGN KEY REFERENCES " +
                            field.getAnnotation(ManyToOne.class).targetTable() +
                            " (" + field.getAnnotation(ManyToOne.class).referenceColumn() + ")";
                }
                query = query + columnInfo + ",";
            }
        }
        return query.substring(0, query.length() - 1) + ")";
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
