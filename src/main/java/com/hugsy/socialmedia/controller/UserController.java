package com.hugsy.socialmedia.controller;

import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.model.User;
import com.hugsy.socialmedia.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}/posts")
    @ResponseBody
    public List<Post> getAllUserPosts(@PathVariable Long id) {
        return userService.getUserPosts(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteById(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
