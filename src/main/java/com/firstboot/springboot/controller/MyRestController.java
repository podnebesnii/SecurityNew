package com.firstboot.springboot.controller;

import com.firstboot.springboot.model.User;
import com.firstboot.springboot.repository.RoleRepository;
import com.firstboot.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class MyRestController {
    private final RoleRepository roleRepository;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public MyRestController(RoleRepository roleRepository, UserServiceImpl userServiceImpl) {
        this.roleRepository = roleRepository;
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/rest/principal")
    public User getPrincipalInfo(Principal principal) {
        return userServiceImpl.findByUserName(principal.getName());
    }

    @GetMapping("/rest")
    public List<User> findAllUsers() {
        return userServiceImpl.findAll();
    }

    @GetMapping("/rest/{id}")
    public User findOneUser(@PathVariable long id) {
        return userServiceImpl.findUserById(id);
    }

    @PostMapping("/rest")
    public ResponseEntity addNewUser(@RequestBody User user) {

        roleRepository.saveAll(user.getRoles());

        userServiceImpl.saveAndFlush(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/rest/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long id) {
        roleRepository.saveAll(user.getRoles());
        userServiceImpl.saveAndFlush(user);
        return user;
    }

    @DeleteMapping("/rest/{id}")
    public void deleteUser(@PathVariable long id) {
        userServiceImpl.deleteById(id);
    }
}
