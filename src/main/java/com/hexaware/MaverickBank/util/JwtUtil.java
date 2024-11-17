package com.hexaware.MaverickBank.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.SECRET_KEY}")
    private String SECRET_KEY ;
    @Value("${jwt.EXPIRATION_TIME}")
    private long EXPIRATION_TIME ;

    /**
     * Generate a token with user details.
     *
     * @param email    the user's email (unique identifier)
     * @param roleName the user's role (e.g., Admin, Customer, Employee)
     * @return the generated JWT token
     */
    public String generateToken(String email, String roleName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", roleName); // Add role as a claim
        return createToken(claims, email);
    }

    /**
     * Create a JWT token with claims and subject.
     *
     * @param claims  additional claims to include in the token
     * @param subject the subject (usually the user's email)
     * @return the generated JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Validate a token's authenticity and expiration.
     *
     * @param token the JWT token
     * @param email the user's email to match the token's subject
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token, String email) {
        String extractedEmail = getUsernameFromToken(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    /**
     * Extract the username (email) from the token.
     *
     * @param token the JWT token
     * @return the email from the token
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Extract the role from the token.
     *
     * @param token the JWT token
     * @return the role from the token
     */
    public String getRoleFromToken(String token) {
        return (String) getClaimsFromToken(token).get("role");
    }

    /**
     * Extract claims from the token.
     *
     * @param token the JWT token
     * @return the claims from the token
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Check if the token is expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }
}