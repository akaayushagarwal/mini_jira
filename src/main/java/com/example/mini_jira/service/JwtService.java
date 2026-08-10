package com.example.mini_jira.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = "MySecretKeyWhichIsPerfectForThisServer";

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
            .signWith(getSigningKey())
            .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
