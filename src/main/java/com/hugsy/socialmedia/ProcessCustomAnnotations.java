package com.hugsy.socialmedia;

import com.hugsy.customorm.QueryBuilder;
import com.hugsy.customorm.annotation.Table;
import com.hugsy.database.Database;
import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.model.User;
import org.springframework.boot.CommandLineRunner;

import java.sql.SQLException;

public class ProcessCustomAnnotations implements CommandLineRunner {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        database.tableExist(User.class.getAnnotation(Table.class).name());

        if (User.class.isAnnotationPresent(Table.class)) {
            if (!database.tableExist(User.class.getAnnotation(Table.class).name())) {
                database.executeQuery(QueryBuilder.createTable(User.class));
            }
        }
        if (Post.class.isAnnotationPresent(Table.class)) {
            if (!database.tableExist(Post.class.getAnnotation(Table.class).name())) {
                database.executeQuery(QueryBuilder.createTable(Post.class));
            }
        }

        database.findById(User.class, 1L, "id", "name", "username");
//        database.deleteById(User.class, 1L);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
