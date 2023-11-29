package com.springProject.springboot.Main.service;

import com.springProject.springboot.Main.Repository.RolesRepository;
import com.springProject.springboot.Main.Repository.UserRepository;
import com.springProject.springboot.Main.entities.Roles;
import com.springProject.springboot.Main.entities.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository , PasswordEncoder passwordEncoder, RolesRepository rolesRepository){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.rolesRepository=rolesRepository;
    }
    @Transactional
    public void saveUser(String username, String password, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        Users users = new Users(username, encodedPassword,email);
        users.setEnable(true);
        userRepository.save(users);
        rolesRepository.save(new Roles(username,"ROLE_AUTHOR"));
    }
}
