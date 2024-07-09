package com.north.pocsecurity.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    @Secured({"user"})
    public List<UserProfile> userGetAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/admin")
    @Secured({"admin"})
    public List<UserProfile> adminGetAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/system")
    @Secured({"system"})
    public List<UserProfile> systemGetAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public UserProfile createUser(@RequestBody UserProfile user) {
        return userRepository.save(user);
    }
}