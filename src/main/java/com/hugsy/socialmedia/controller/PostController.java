package com.hugsy.socialmedia.controller;

import com.hugsy.socialmedia.model.Post;
import com.hugsy.socialmedia.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/posts")
    @ResponseBody
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteById(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
