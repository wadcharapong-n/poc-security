package com.north.pocsecurity.filter;

import com.north.pocsecurity.domain.user.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

import static com.north.pocsecurity.security.SecurityConstants.HEADER_STRING;
import static com.north.pocsecurity.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAdminAuthorizationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UserProfile userProfile = getUserProfile(req);
        if("ADMIN".equalsIgnoreCase(userProfile.getRole())) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(userProfile);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }
    private  UserProfile getUserProfile(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);
        Claims claims = (Claims) Jwts.parserBuilder()
                .build()
                .parse(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        Integer userId = (Integer) claims.get("I_USER");
        String username = (String) claims.get("U_NAME");
        List<String> roles = (List<String>) claims.get("U_ROLES");
        UserProfile userProfile = new UserProfile();
        userProfile.setId(Long.valueOf(userId));
        userProfile.setName(username);
        userProfile.setRole(roles.getFirst());
        return userProfile;
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(UserProfile userProfile) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userProfile.getRole());
        // new arraylist means authorities
        return new UsernamePasswordAuthenticationToken(userProfile.getName(), userProfile, List.of(simpleGrantedAuthority));
    }
}
