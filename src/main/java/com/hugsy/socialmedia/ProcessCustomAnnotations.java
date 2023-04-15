package com.hugsy.socialmedia;

import com.hugsy.customorm.annotation.Table;
import com.hugsy.database.Database;
import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.model.User;

import java.sql.SQLException;

public class ProcessCustomAnnotations {
    public static void main(String[] args) throws SQLException {
        Database database = Database.getInstance();
        database.tableExist(User.class.getAnnotation(Table.class).name());

        if (User.class.isAnnotationPresent(Table.class)) {
            if (!database.tableExist(User.class.getAnnotation(Table.class).name())) {
                database.createTable(User.class);
            }
        }
        if (Post.class.isAnnotationPresent(Table.class)) {
            if (!database.tableExist(Post.class.getAnnotation(Table.class).name())) {
                database.createTable(Post.class);
            }
        }

    }

}
