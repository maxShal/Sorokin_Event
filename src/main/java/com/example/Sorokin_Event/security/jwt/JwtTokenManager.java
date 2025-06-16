package com.example.Sorokin_Event.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenManager    {

    private final SecretKey key;
    private final long expirationTime;

    public JwtTokenManager(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.lifetime}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationTime = expirationTime;
    }

    public String generateToken(Authentication authentication){
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .signWith(key)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expirationTime))
                .compact();
    }

    public String getLoginFromToken(String token){

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
