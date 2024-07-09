package com.north.pocsecurity.domain.auth;

import com.north.pocsecurity.domain.user.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @GetMapping
    public String authentication() {
        return jwtService.signingByJWT("code", 1, "user", null, 60);
    }
}
