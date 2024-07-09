package com.north.pocsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;

import static com.north.pocsecurity.security.SecurityConstants.HEADER_STRING;
import static com.north.pocsecurity.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            Claims claims = (Claims) Jwts.parserBuilder()
                    .build()
                    .parse(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            Object i_user = claims.get("I_USER");
            if (i_user != null) {
                // new arraylist means authorities
                Long userId = ((Integer) i_user).longValue();
                return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
