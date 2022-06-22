package com.firstboot.springboot.controller;

import com.firstboot.springboot.model.User;
import com.firstboot.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller

public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/user")
    public String userInfo(Principal principal, Model model){
        User user = userServiceImpl.findByUserName(principal.getName());
        model.addAttribute("activeUser", user);
        return "user";
    }
}
