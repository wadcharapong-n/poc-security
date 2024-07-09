package com.north.pocsecurity.security;

import com.north.pocsecurity.filter.ApiKeyAuthorizationFilter;
import com.north.pocsecurity.filter.JwtAdminAuthorizationFilter;
import com.north.pocsecurity.filter.JwtUserAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity(
        securedEnabled = true
)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        JwtUserAuthorizationFilter jwtAuthorizationFilter = new JwtUserAuthorizationFilter();
        JwtAdminAuthorizationFilter jwtAdminAuthorizationFilter = new JwtAdminAuthorizationFilter();
        ApiKeyAuthorizationFilter apiKeyAuthorizationFilter = new ApiKeyAuthorizationFilter();
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/authentication/**"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAdminAuthorizationFilter, JwtUserAuthorizationFilter.class)
                .addFilterBefore(apiKeyAuthorizationFilter, JwtAdminAuthorizationFilter.class)
        ;
        return http.build();
    }
}
