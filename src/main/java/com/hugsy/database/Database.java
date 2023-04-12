package com.hugsy.database;

import com.hugsy.customorm.annotation.Id;
import com.hugsy.customorm.annotation.Table;

import java.sql.*;
import java.util.Arrays;

public class Database {
    private Connection connection;
    private Statement statement;

    public Database() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=social_media;user=sa;password=123456;encrypt=true;trustServerCertificate=true;";
            this.connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                System.out.println("Connected");
                this.connection = connection;
                this.statement = connection.createStatement();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Integer executeQuery(String query) throws SQLException {
        Integer result = statement.executeUpdate(query);
        return result;
    }

    public boolean tableExistsSQL(String tableName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM social_media.tables "
                + "WHERE table_name = ?;");
        preparedStatement.setString(1, tableName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
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

    public ResultSet findById(Class<?> c, Long id) throws SQLException {
        String query = "Select * from " + c.getAnnotation(Table.class).name() +
                " where " + getIdFieldName(c) + " = " + id;
        return statement.executeQuery(query);
    }

    public ResultSet findAll(Class<?> c) throws SQLException {
        String query = "Select * from " + c.getAnnotation(Table.class).name();
        return statement.executeQuery(query);
    }

    public void deleteById(Class<?> c, Long id) throws SQLException {
        String query = "delete from " + c.getAnnotation(Table.class).name() +
                " where " + getIdFieldName(c) + " = " + id;
        statement.executeUpdate(query);
    }

    public void deleteByFk(Class<?> c, Long id) throws SQLException {
        String query = "delete from " + c.getAnnotation(Table.class).name() +
                "where " + getIdFieldName(c) + " = " + id;
        statement.executeUpdate(query);
    }

    String getIdFieldName(Class<?> c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get().getName();
    }


}
