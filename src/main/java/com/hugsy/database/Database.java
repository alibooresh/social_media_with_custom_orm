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
            System.out.println(e);
        }
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public Integer executeQuery(String query) throws SQLException {
        Integer result = statement.executeUpdate(query);
        return result;
    }

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

    public ResultSet findById(Class<?> c, Long id, String... fields) throws SQLException {
        ResultSet resultSet = statement.executeQuery(queryBuilder.findById(c, id, fields));
        return resultSet;
    }

    public ResultSet findByFk(Class<?> c, String fkColumn, Long fkId, String... fields) throws SQLException {
        ResultSet resultSet = statement.executeQuery(queryBuilder.findByFk(c, fkColumn, fkId, fields));
        return resultSet;
    }

    public ResultSet findAll(Class<?> c, String... fields) throws SQLException {
        return statement.executeQuery(queryBuilder.findAll(c, fields));
    }

    public void deleteById(Class<?> c, Long id) throws SQLException {
        statement.executeUpdate(queryBuilder.deleteById(c, id));
    }

    public void deleteByFk(Class<?> c, String fkColumn, Long fkId) throws SQLException {
        statement.executeUpdate(queryBuilder.deleteByFk(c, fkColumn, fkId));
    }

}
