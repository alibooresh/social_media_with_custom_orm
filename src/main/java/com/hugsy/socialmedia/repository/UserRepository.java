package com.hugsy.socialmedia.repository;

import com.hugsy.socialmedia.model.User;

import java.util.List;

public interface UserRepository {

    User findById(Long id);

    void deleteById(Long id);

    List<User> findAll();

}
