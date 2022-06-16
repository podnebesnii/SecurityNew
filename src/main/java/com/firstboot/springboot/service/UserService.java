package com.firstboot.springboot.service;

import com.firstboot.springboot.model.User;

import java.util.List;

public interface UserService {
    public User findById(Integer id);

    public List<User> findAll();

    public boolean saveUser(User user);

    public void deleteById(Integer id);
}
