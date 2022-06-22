package com.firstboot.springboot.service;

import com.firstboot.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    List<User> findAll();

    User findByEmail(String email);

    boolean saveUser(User user);

    void deleteById(Integer id);
}
