package com.hugsy.socialmedia.service;

import com.hugsy.socialmedia.model.Post;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    public static String POST_PREFIX = "post";

    private Jedis jedis;

    public static String postToJsonString(Post post) {
        if (post == null) {
            return null;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", post.getId());
            jsonObject.put("caption", post.getCaption());
            jsonObject.put("media_url", post.getMediaUrl());
            jsonObject.put("user_id", post.getUserId());
            return jsonObject.toString();
        }
    }

    public static Post stringJsonToPost(String json) {
        Post post = new Post();
        if (json == null) {
            return null;
        } else {
            JSONObject jsonObject = new JSONObject(json);
            post.setId(Long.valueOf(String.valueOf(jsonObject.get("id"))));
            post.setCaption((String) jsonObject.get("caption"));
            post.setMediaUrl((String) jsonObject.get("media_url"));
            post.setUserId(Long.valueOf(String.valueOf(jsonObject.get("user_id"))));
            return post;
        }
    }

    public Post getPost(Long id) {
        jedis = new Jedis();
        return stringJsonToPost(jedis.hget(POST_PREFIX, id.toString()));
    }

    public List<Post> getPosts() {
        jedis = new Jedis();
        List<Post> posts = new ArrayList<>();
        Map<String, String> map = jedis.hgetAll(POST_PREFIX);
        map.forEach((s, s2) -> {
            posts.add(stringJsonToPost(s2));
        });
        return posts;
    }

    public List<Post> getUserPosts(Long userId) {
        jedis = new Jedis();
        List<Post> posts = getPosts().stream().filter(post -> post.getUserId().equals(userId)).toList();
        return posts;
    }

    public static Post mapPostResult(ResultSet resultSet) throws SQLException {
        Post post;
        if (resultSet == null) {
            post = null;
        } else {
            post = new Post();
            while (resultSet.next()) {
                post.setId(resultSet.getLong("id"));
                post.setCaption(resultSet.getString("caption"));
                post.setMediaUrl(resultSet.getString("media_url"));
                post.setUserId(resultSet.getLong("user_id"));
            }
            if (post.getId() == null) {
                post = null;
            }
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
