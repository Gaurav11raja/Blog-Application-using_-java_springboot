package com.springProject.springboot.Main.controller;

import com.springProject.springboot.Main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {
    private UserService userService;
    @Autowired
    public UsersController(UserService userService){
        this.userService=userService;
    }


    @GetMapping("/showMyLoginPage")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/showMySignUpPage")
    public String signUpPage(){
        return "createUser";
    }
    @GetMapping("/access-denied")
    public String denied(){
        return "redirect:/";
    }
    @PostMapping("/register")
    public String createNewUser(@RequestParam("username")String username, @RequestParam("password")String password,
                                @RequestParam("email")String email){
        userService.saveUser(username,password,email);
        return "login";
    }
}
