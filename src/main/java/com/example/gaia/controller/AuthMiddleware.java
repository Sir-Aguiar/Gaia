package com.example.gaia.controller;

import com.example.gaia.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthMiddleware extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (request.getRequestURI().equals("/user/sign-in") || request.getRequestURI().equals("/user/sign-up")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String jwtToken = authHeader.substring(7);

      try {
        if (JWTService.validateToken(jwtToken, JWTService.getUsernameFromToken(jwtToken))) {
          filterChain.doFilter(request, response);
          return;
        }
      } catch (Exception exception) {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token expirado");
        return;
      }
    }

    response.sendError(HttpStatus.FORBIDDEN.value(), "Nenhuma sessão foi encontrada");
  }
}
