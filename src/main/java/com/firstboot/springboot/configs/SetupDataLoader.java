package com.firstboot.springboot.configs;

import com.firstboot.springboot.model.Role;
import com.firstboot.springboot.model.User;
import com.firstboot.springboot.repository.RoleRepository;
import com.firstboot.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


/**
 *
 */

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final static long ROLE_ADMIN = 1;
    private final static long ROLE_USER = 2;

    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Iterable<User> users = userRepository.findAll();
        alreadySetup = users.iterator().hasNext();
        if (alreadySetup) {
                //если пользователи уже есть, ничего делать не надо
        } else {
            Role adminRole = new Role(ROLE_ADMIN, "ROLE_ADMIN");
            Role userRole = new Role(ROLE_USER, "ROLE_USER");
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            User admin = new User();
            admin.setName("admin");
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setPassword("admin");
            admin.setRoles(adminRoles);
            userRepository.save(admin);

            User user = new User();
            user.setName("user");
            user.setFirstName("User");
            user.setLastName("User");
            user.setPassword("user");
            user.setRoles(userRoles);
            userRepository.save(user);
            alreadySetup = true;
        }
    }
}


