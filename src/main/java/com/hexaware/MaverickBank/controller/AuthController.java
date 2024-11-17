package com.hexaware.MaverickBank.controller;

import com.hexaware.MaverickBank.service.impl.CustomUserDetailsService;
import com.hexaware.MaverickBank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            // Fetch the user (Customer or Employee)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // Generate JWT token with role
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String token = jwtUtil.generateToken(email, role);


            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {

            throw new RuntimeException("Invalid email or password");
        }
    }
}