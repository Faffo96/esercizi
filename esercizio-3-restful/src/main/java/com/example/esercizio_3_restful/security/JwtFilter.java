package com.example.esercizio_3_restful.security;

import com.example.esercizio_3_restful.Entity.User;
import com.example.esercizio_3_restful.exception.UserNotFoundException;
import com.example.esercizio_3_restful.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleUnauthorized(response, "Error in authorization, token missing or does not start with 'Bearer '!");
            return;
        }

        String token = authHeader.substring(7);

        try {
            jwtTool.verifyToken(token);
        } catch (Exception e) {
            handleUnauthorized(response, "Invalid token!");
            return;
        }

        String userEmail = jwtTool.getEmailFromToken(token);

        User user;
        try {
            user = userService.getUserByEmail(userEmail);
        } catch (UserNotFoundException e) {
            logger.error("User with email=" + userEmail + " not found");
            handleUnauthorized(response, "User with email=" + userEmail + " not found");
            return;
        }



        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathRequestMatcher("/auth/**").matches(request);
    }

    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}