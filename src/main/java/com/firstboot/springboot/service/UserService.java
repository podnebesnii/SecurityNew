package com.firstboot.springboot.service;

import com.firstboot.springboot.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUserById(long id);

    void saveAndFlush(User user);

    void deleteById(long id);

}
