package com.hugsy.socialmedia.service;

import com.hugsy.socialmedia.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public static User mapUserResult(ResultSet resultSet) throws SQLException {
        User user;
        if (resultSet == null) {
            user = null;
        } else {
            user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
            }
            if (user.getId() == null) {
                user = null;
            }
        }
        return user;
    }

    public static List<User> mapUsersResult(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            users.add(user);
        }
        return users;
    }
}
