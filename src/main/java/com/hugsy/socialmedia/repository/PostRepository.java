package com.hugsy.socialmedia.repository;

import com.hugsy.socialmedia.model.Post;

import java.util.List;

public interface PostRepository {

    Post findById(Long id);

    void deleteById(Long id);

    List<Post> findAll();

    void deleteByFk(Long fkId);
}
