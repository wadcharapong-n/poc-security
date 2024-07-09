package com.north.pocsecurity;

import com.north.pocsecurity.domain.user.UserProfile;
import com.north.pocsecurity.domain.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Insert initial data
        UserProfile user1 = new UserProfile();
        user1.setName("menkung 66");
        user1.setEmail("menkung@example.com");
        user1.setPassword("$2a$10$D9QOQ/E2OYV8EmJfMZKQ5OdQDp8z3Gh/nOo.I7nF7a6NPe/I8/x.q"); // password

        UserProfile user2 = new UserProfile();
        user2.setName("wadcharapong 66");
        user2.setEmail("wadcharapong@example.com");
        user2.setPassword("$2a$10$D9QOQ/E2OYV8EmJfMZKQ5OdQDp8z3Gh/nOo.I7nF7a6NPe/I8/x.q"); // password

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
