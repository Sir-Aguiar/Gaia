package com.example.gaia.config;

import com.example.gaia.controller.AuthMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.example.gaia.controller")
public class SecurityConfig {
  private final AuthMiddleware authMiddleware;

  @Autowired
  public SecurityConfig(AuthMiddleware authMiddleware) {
    this.authMiddleware = authMiddleware;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/user/sign-in", "/user/sign-up").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(authMiddleware, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}