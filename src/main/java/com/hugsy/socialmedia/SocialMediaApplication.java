package com.hugsy.socialmedia;

import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.model.User;
import com.hugsy.socialmedia.service.PostService;
import com.hugsy.socialmedia.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class SocialMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApplication.class, args);
        Jedis jedis = new Jedis();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Jack Nicholson");
        user1.setUsername("jack123");
        String userPrefix = "user";
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Nich Jakelson");
        user2.setUsername("nic123");
        jedis.hset(userPrefix, user1.getId().toString(), UserService.userToJsonString(user1));
        jedis.hset(userPrefix, user2.getId().toString(), UserService.userToJsonString(user2));

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

        jedis.hset(PostService.POST_PREFIX, post1.getId().toString(), PostService.postToJsonString(post1));
        jedis.hset(PostService.POST_PREFIX, post2.getId().toString(), PostService.postToJsonString(post2));

    }

}
