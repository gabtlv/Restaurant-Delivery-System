package com.restaurant.menu.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JWTUtil {

    // Secret key — must be the same in all three services
    private static final String SECRET = "mySecretKeyForJWTAuthenticationLab4";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Create a JWT token for the logged in user
    public static String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Verify a JWT token — returns email if valid, null if invalid
    public static String verifyToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}