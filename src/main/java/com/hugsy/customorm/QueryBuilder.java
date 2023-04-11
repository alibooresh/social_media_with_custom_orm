package com.hugsy.customorm;

import com.hugsy.customorm.annotation.Column;
import com.hugsy.customorm.annotation.Table;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class QueryBuilder {
    public static String createTable(@NotNull Class<?> aClass) {
        String query = "create table " + aClass.getAnnotation(Table.class).name() + "(";
        if (aClass.getFields().length > 0) {
            for (Field field : aClass.getFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    String columnInfo = field.getAnnotation(Column.class).name() + " " +
                            getType(field);
                    query = query + " " + columnInfo;
                }
            }
        }
        return query + ")";
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
