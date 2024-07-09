package com.north.pocsecurity.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public UserProfile createUser(@RequestBody UserProfile user) {
        return userRepository.save(user);
    }
}