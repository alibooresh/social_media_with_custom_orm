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
    public String createTable(Class<?> c) {
        StringBuilder query = new StringBuilder("create table " + c.getAnnotation(Table.class).name() + "(");
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
                query.append(columnInfo).append(",");
            }
        }
        return query.substring(0, query.length() - 1) + ")";
    }

    /**
     * Returns the type of the specified field object in string
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
     * Returns a select query to get an entity columns by id
     *
     * @param c      class of database table to get table name
     * @param id     record id to select
     * @param fields columns to select
     * @return a select query
     */
    public String findById(Class<?> c, Long id, String... fields) {
        return "Select " + getFields(fields) + " from " + c.getAnnotation(Table.class).name() +
                " where " + getIdFieldName(c) + " = " + id;
    }

    /**
     * Returns a select query to get an entity columns by foreign key id
     *
     * @param c        class of database table to get table name
     * @param fkColumn foreign key column name
     * @param fkId     foreign key column id
     * @param fields   columns to select
     * @return a select query to get an entity by foreign key id
     */
    public String findByFk(Class<?> c, String fkColumn, Long fkId, String... fields) {
        return "Select " + getFields(fields) + " from " + c.getAnnotation(Table.class).name() +
                " where " + fkColumn + " = " + fkId;
    }

    /**
     * Returns a select query to get all records of given table (c)
     *
     * @param c      class of database table to get table name
     * @param fields columns to select
     * @return a select select query to get all records
     */
    public String findAll(Class<?> c, String... fields) {
        return "Select " + getFields(fields) + " from " + c.getAnnotation(Table.class).name();
    }

    /**
     * Returns a query to delete record from given table (c) where id column is id
     *
     * @param c class of database table to get table name
     * @param id record id to delete
     * @return a delete query to delete record by id
     */
    public String deleteById(Class<?> c, Long id) {
        return "delete from " + c.getAnnotation(Table.class).name() +
                " where " + getIdFieldName(c) + " = " + id;
    }

    /**
     * Returns a query to delete record(s) from given table (c) where foreign key column is fkId
     *
     * @param c class of database table to get table name
     * @param fkColumn foreign key column
     * @param fkId foreign key id
     * @return a delete query to delete record(s) by fkId
     */
    public String deleteByFk(Class<?> c, String fkColumn, Long fkId) {
        return "delete from " + c.getAnnotation(Table.class).name() +
                " where " + fkColumn + " = " + fkId;
    }

    /**
     * Returns the id column name of given table (c)
     *
     * @param c class of database table to get table name
     * @return return id column name
     */
    String getIdFieldName(Class<?> c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get().getName();
    }

    /**
     * Return string of given fields to use in select queries
     *
     * @param fields fields of class
     * @return string of given fields
     */
    String getFields(String... fields) {
        if (fields.length == 0) {
            return "*";
        } else {
            StringBuilder result = new StringBuilder();
            for (String field : fields) {
                result.append(field).append(",");
            }
            return result.substring(0, result.length() - 1);
        }
    }

}
