package com.hugsy.database;

import java.sql.*;

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
}