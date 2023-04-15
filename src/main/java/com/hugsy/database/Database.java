package com.hugsy.database;

import com.hugsy.customorm.QueryBuilder;

import java.sql.*;

/**
 * Provide a instance to connect to database and execute query
 */
public final class Database {
    private static Database INSTANCE;
    private Connection connection;
    private Statement statement;
    private QueryBuilder queryBuilder;

    private Database() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=social_media;user=sa;password=123456;encrypt=true;trustServerCertificate=true;";
            this.connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                queryBuilder = new QueryBuilder();
                this.statement = connection.createStatement();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    /**
     * Run a create table query to create given table(c)
     *
     * @param c class of database table to get table name
     * @return either (1) the row count for SQL (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws SQLException throws an SQL Exception
     */
    public Integer createTable(Class<?> c) throws SQLException {
        return statement.executeUpdate(queryBuilder.createTable(c));
    }

    /**
     * Check existence of given table name
     *
     * @param tableName table name to check
     * @return true if the given table exist, false otherwise
     * @throws SQLException throws an SQL Exception
     */
    public boolean tableExist(String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = this.connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }

    /**
     * Return a ResultSet of given columns(fields) from given table name (c) with specified id
     *
     * @param c      class of database table to get table name
     * @param id     id to load
     * @param fields columns to load
     * @return ResultSet
     * @throws SQLException throws an SQL Exception
     */
    public ResultSet findById(Class<?> c, Long id, String... fields) throws SQLException {
        return statement.executeQuery(queryBuilder.findById(c, id, fields));
    }

    /**
     * Return a ResultSet of given columns(fields) from given table name (c) by fkColumn and fkId
     *
     * @param c        class of database table to get table name
     * @param fkColumn foreign key column name
     * @param fkId     foreign key id
     * @param fields   columns to load
     * @return A resultSet
     * @throws SQLException throws an SQL Exception
     */
    public ResultSet findByFk(Class<?> c, String fkColumn, Long fkId, String... fields) throws SQLException {
        return statement.executeQuery(queryBuilder.findByFk(c, fkColumn, fkId, fields));
    }

    /**
     * Gets the table name of class (c) and select all records in database
     *
     * @param c      class of database table to get table name
     * @param fields columns to load
     * @return a resultSet
     * @throws SQLException throws an SQL Exception
     */
    public ResultSet findAll(Class<?> c, String... fields) throws SQLException {
        return statement.executeQuery(queryBuilder.findAll(c, fields));
    }

    /**
     * Delete a record from the table (c) where id column is id
     *
     * @param c  class of database table to get table name
     * @param id id to delete
     * @throws SQLException throws an SQL Exception
     */
    public void deleteById(Class<?> c, Long id) throws SQLException {
        statement.executeUpdate(queryBuilder.deleteById(c, id));
    }

    /**
     * Delete record(s) from the table (c) where foreign key column is fkColumn
     *
     * @param c        class of database table to get table name
     * @param fkColumn foreign key column name
     * @param fkId     foreign key id
     * @throws SQLException throws an SQL Exception
     */
    public void deleteByFk(Class<?> c, String fkColumn, Long fkId) throws SQLException {
        statement.executeUpdate(queryBuilder.deleteByFk(c, fkColumn, fkId));
    }

}
