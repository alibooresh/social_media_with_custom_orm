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
        Database database = new Database();
        database.tableExist(User.class.getAnnotation(Table.class).name());
        User user = new User();
        user.setName("Jerry");
        user.setUsername("jrr");
        Post post = new Post();
        post.setCaption("Test Caption");
        post.setMediaUrl("http://ttttt");
        if (User.class.isAnnotationPresent(Table.class)) {
            if (!database.tableExist(User.class.getAnnotation(Table.class).name())) {
                database.executeQuery(QueryBuilder.createTable(user.getClass()));
            }
        }
        if (Post.class.isAnnotationPresent(Table.class)) {
            if (!database.tableExist(Post.class.getAnnotation(Table.class).name())) {
                database.executeQuery(QueryBuilder.createTable(Post.class));
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
