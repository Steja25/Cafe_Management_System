package com.cafe_management.controller;

import com.cafe_management.model.User;
import com.cafe_management.repository.UserRepository;
import com.cafe_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User users=new User();
        users.setUsername(user.getUsername());
        users.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(users);

        return ResponseEntity.ok( );


    }

    public ResponseEntity<String> loginUser(@RequestBody User user) {

    }


}
