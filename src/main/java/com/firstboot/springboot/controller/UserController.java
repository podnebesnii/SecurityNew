package com.firstboot.springboot.controller;

import com.firstboot.springboot.model.Role;
import com.firstboot.springboot.model.User;
import com.firstboot.springboot.repository.RoleRepository;
import com.firstboot.springboot.repository.UserRepository;
import com.firstboot.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/user")
    public String showUserInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/{id}")
    public String updateUser(User user, @PathVariable int id, @RequestParam(value = "role") String[] roles) {
        user.setRoles(getRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser (@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles) {
        user.setRoles(getRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }
    public Set<Role> getRoles(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(roleRepository.findByName(role));
        }
        return roleSet;
    }
}
