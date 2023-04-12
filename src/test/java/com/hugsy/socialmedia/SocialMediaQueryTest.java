package com.hugsy.socialmedia;

import com.hugsy.database.Database;
import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.model.User;
import com.hugsy.socialmedia.service.PostService;
import com.hugsy.socialmedia.service.UserService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class SocialMediaQueryTest {

    @Test
    public void testFindUserById() throws SQLException {
        Database database = new Database();
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("Jack Nicholson");
        expectedUser.setUsername("jack123");
        User resultUser = UserService.mapUserResult(database.findById(User.class, 1L, "id", "name", "username"));
        assertEquals(expectedUser.getId(), resultUser.getId());
        assertEquals(expectedUser.getName(), resultUser.getName());
        assertEquals(expectedUser.getUsername(), resultUser.getUsername());
    }

    @Test
    void testFindAllUsers() throws SQLException {
        Database database = new Database();
        List<User> expectedUsers = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Jack Nicholson");
        user1.setUsername("jack123");
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Nich Jakelson");
        user2.setUsername("nic123");
        expectedUsers.add(user1);
        expectedUsers.add(user2);
        List<User> actualUsers = UserService.mapUsersResult(database.findAll(User.class));
        assertEquals(expectedUsers.size(), actualUsers.size());
        actualUsers.forEach(actualUser -> {
            expectedUsers.forEach(expectedUser -> {
                if (Objects.equals(actualUser.getId(), expectedUser.getId())) {
                    assertEquals(expectedUser.getId(), actualUser.getId());
                    assertEquals(expectedUser.getName(), actualUser.getName());
                    assertEquals(expectedUser.getUsername(), actualUser.getUsername());
                }
            });
        });
    }

    @Test
    public void testFindPostById() throws SQLException {
        Database database = new Database();
        Post expectedPost = new Post();
        expectedPost.setId(1L);
        expectedPost.setCaption("Life is simple. It’s just not easy.");
        expectedPost.setMediaUrl("http://nazdika.com/1");
        expectedPost.setUserId(1L);
        Post resultPost = PostService.mapPostResult(database.findById(Post.class, 1L, "id", "caption", "media_url", "user_id"));
        assertEquals(expectedPost.getId(), resultPost.getId());
        assertEquals(expectedPost.getCaption(), resultPost.getCaption());
        assertEquals(expectedPost.getMediaUrl(), resultPost.getMediaUrl());
        assertEquals(expectedPost.getUserId(), resultPost.getUserId());
    }

    @Test
    void testFindAllPosts() throws SQLException {
        Database database = new Database();
        List<Post> expectedPosts = new ArrayList<>();
        Post post1 = new Post();
        post1.setId(1L);
        post1.setCaption("Life is simple. It’s just not easy.");
        post1.setMediaUrl("http://nazdika.com/1");
        post1.setUserId(1L);
        Post post2 = new Post();
        post2.setId(2L);
        post2.setCaption("Life is too short for bad vibes.");
        post2.setMediaUrl("http://nazdika.com/2");
        post2.setUserId(1L);
        expectedPosts.add(post1);
        expectedPosts.add(post2);
        List<Post> actualPosts = PostService.mapPostsResult(database.findAll(Post.class));
        assertEquals(expectedPosts.size(), actualPosts.size());
        actualPosts.forEach(actualPost -> {
            expectedPosts.forEach(expectedPost -> {
                if (Objects.equals(actualPost.getId(), expectedPost.getId())) {
                    assertEquals(expectedPost.getId(), actualPost.getId());
                    assertEquals(expectedPost.getCaption(), actualPost.getCaption());
                    assertEquals(expectedPost.getMediaUrl(), actualPost.getMediaUrl());
                    assertEquals(expectedPost.getUserId(), actualPost.getUserId());
                }
            });
        });
    }
}
