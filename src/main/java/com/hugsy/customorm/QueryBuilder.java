package com.hugsy.customorm;

import com.hugsy.customorm.annotation.Column;
import com.hugsy.customorm.annotation.Id;
import com.hugsy.customorm.annotation.ManyToOne;
import com.hugsy.customorm.annotation.Table;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Utility class for building SQL query
 */
public class QueryBuilder {

    /**
     * Gets the class and generate SQL query for creating class table in database
     *
     * @param c Class
     * @return SQL query for creating class table in database
     */
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

    /**
     * Return the type of the specified field object
     *
     * @param field the field object
     * @return type of the field object
     */
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

    /**
     * Return a select query to get an entity columns by id
     *
     * @param c      class
     * @param id     entity id
     * @param fields fields to load
     * @return
     */
    public String findById(Class<?> c, Long id, String... fields) {
        return "Select " + getFields(fields) + " from " + c.getAnnotation(Table.class).name() +
                " where " + getIdFieldName(c) + " = " + id;
    }

    /**
     * Return a select query to get an entity columns by foreign key id
     *
     * @param c        class
     * @param fkColumn foreign key column name
     * @param fkId     foreign key column id
     * @param fields   fields to load
     * @return a select query
     */
    public String findByFk(Class<?> c, String fkColumn, Long fkId, String... fields) {
        return "Select " + getFields(fields) + " from " + c.getAnnotation(Table.class).name() +
                " where " + fkColumn + " = " + fkId;
    }

    public String findAll(Class<?> c, String... fields) {
        return "Select " + getFields(fields) + " from " + c.getAnnotation(Table.class).name();
    }

    public String deleteById(Class<?> c, Long id) {
        return "delete from " + c.getAnnotation(Table.class).name() +
                " where " + getIdFieldName(c) + " = " + id;
    }

    public String deleteByFk(Class<?> c, String fkColumn, Long fkId) {
        return "delete from " + c.getAnnotation(Table.class).name() +
                " where " + fkColumn + " = " + fkId;
    }

    String getIdFieldName(Class<?> c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get().getName();
    }

    String getFields(String... fields) {
        String result = "";
        for (String field : fields) {
            result += field + ",";
        }
        return result.substring(0, result.length() - 1);
    }

}
