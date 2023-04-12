package com.hugsy.socialmedia.service;

import com.hugsy.socialmedia.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    public static Post mapPostResult(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        while (resultSet.next()) {
            post.setId(resultSet.getLong("id"));
            post.setCaption(resultSet.getString("caption"));
            post.setMediaUrl(resultSet.getString("media_url"));
            post.setUserId(resultSet.getLong("user_id"));
        }
        return post;
    }

    public static List<Post> mapPostsResult(ResultSet resultSet) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            Post post = new Post();
            post.setId(resultSet.getLong("id"));
            post.setCaption(resultSet.getString("caption"));
            post.setMediaUrl(resultSet.getString("media_url"));
            post.setUserId(resultSet.getLong("user_id"));
            posts.add(post);
        }
        return posts;
    }

}
