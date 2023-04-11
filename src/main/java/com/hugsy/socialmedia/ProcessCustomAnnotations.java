package com.hugsy.socialmedia;

import com.hugsy.customorm.QueryBuilder;
import com.hugsy.customorm.annotation.Table;
import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.model.User;

public class ProcessCustomAnnotations {
    public static void main(String[] args) {
        User user = new User();
        user.setName("Jerry");
        user.setUsername("jrr");
        Post post = new Post();
        post.setCaption("Test Caption");
        post.setMediaUrl("http://ttttt");
        if (user.getClass().isAnnotationPresent(Table.class)){
            System.out.println(user.getClass().getAnnotation(Table.class).name());
            System.out.println(QueryBuilder.createTable(user.getClass()));
            System.out.println(QueryBuilder.createTable(post.getClass()));
        }
    }
}
