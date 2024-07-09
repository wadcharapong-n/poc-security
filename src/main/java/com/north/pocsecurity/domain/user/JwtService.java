package com.north.pocsecurity.domain.user;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {


    public String signingByJWT(String code, long userId, String username, Collection<String> rolesName, long minuteTimeOut) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("I_USER", userId)
                .claim("N_LOGIN", username)
                .claim("ROLES", rolesName)
                .claim("CODE", code)
                .claim("TIMESTAMP", new Date())
                .setIssuer("ABC")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(minuteTimeOut).atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }
}
