package com.hugsy.socialmedia.service;

import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.model.User;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private Jedis jedis;
    private String USER_PREFIX = "user";
    private PostService postService;

    public UserService(PostService postService) {
        this.postService = postService;
    }

    public static String userToJsonString(User user) {
        if (user == null) {
            return null;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", user.getId());
            jsonObject.put("name", user.getName());
            jsonObject.put("username", user.getUsername());
            return jsonObject.toString();
        }
    }

    public static User stringJsonToUser(String json) {
        User user = new User();
        if (json == null) {
            return null;
        } else {
            JSONObject jsonObject = new JSONObject(json);
            user.setId(Long.valueOf(String.valueOf(jsonObject.get("id"))));
            user.setName((String) jsonObject.get("name"));
            user.setUsername((String) jsonObject.get("username"));
            return user;
        }
    }

    public User getUser(Long id) {
        jedis = new Jedis();
        return stringJsonToUser(jedis.hget(USER_PREFIX, id.toString()));
    }

    public List<User> getUsers() {
        jedis = new Jedis();
        List<User> users = new ArrayList<>();
        Map<String, String> map = jedis.hgetAll(USER_PREFIX);
        map.forEach((s, s2) -> {
            users.add(stringJsonToUser(s2));
        });
        return users;
    }

    public List<Post> getUserPosts(Long userId) {
        return postService.getUserPosts(userId);
    }

    public void deleteUser(Long id) {
        jedis = new Jedis();
        jedis.hdel(USER_PREFIX, id.toString());
    }

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
