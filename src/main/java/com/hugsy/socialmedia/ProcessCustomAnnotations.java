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
        User user = new User();
        user.setName("Jerry");
        user.setUsername("jrr");
        Post post = new Post();
        post.setCaption("Test Caption");
        post.setMediaUrl("http://ttttt");
        if (user.getClass().isAnnotationPresent(Table.class)) {
            System.out.println(user.getClass().getAnnotation(Table.class).name());
            System.out.println(QueryBuilder.createTable(user.getClass()));
            System.out.println(QueryBuilder.createTable(post.getClass()));
        }
        database.executeQuery(QueryBuilder.createTable(user.getClass()));
        database.executeQuery(QueryBuilder.createTable(post.getClass()));

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
