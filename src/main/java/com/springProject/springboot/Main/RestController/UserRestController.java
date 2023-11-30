package com.springProject.springboot.Main.RestController;

import com.springProject.springboot.Main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return new ResponseEntity<>("Login page", HttpStatus.OK);
    }

    @GetMapping("/signup")
    public ResponseEntity<String> signUpPage() {
        return new ResponseEntity<>("Signup page", HttpStatus.OK);
    }

    @GetMapping("/access-denied")
    public ResponseEntity<String> denied() {

        return new ResponseEntity<>("Access Denied!", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createNewUser(@RequestParam("username") String username,
                                                @RequestParam("password") String password,
                                                @RequestParam("email") String email) {
        try {
            userService.saveUser(username, password, email);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
