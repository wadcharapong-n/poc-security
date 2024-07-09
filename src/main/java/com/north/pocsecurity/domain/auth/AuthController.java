package com.north.pocsecurity.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @GetMapping("/1")
    public String authentication1() {
        return jwtService.signingByJWT("code", 1, "userFromToken", List.of("user"), 60);
    }

    @GetMapping("/2")
    public String authentication2() {
        return jwtService.signingByJWT("code", 2, "adminFromToken", List.of("admin"), 60);
    }
}
