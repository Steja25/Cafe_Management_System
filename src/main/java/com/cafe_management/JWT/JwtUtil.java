package com.cafe_management.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
     @Value("${jwt.secret}")
    private String secret;
     @Value("${jwt.expiration}")
    private long expiration;
public Key getSignKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

    if (keyBytes.length < 32) {
        throw new IllegalArgumentException("JWT Secret must be at least 256 bits (32 bytes)");
    }

    return Keys.hmacShaKeyFor(keyBytes);
}

    public String generateToken(String userName ){
     return Jwts.builder()
             .setSubject(userName)
             .setIssuer("CafeManagementSystem")
             .setIssuedAt(new Date())
             .setExpiration(new Date(System.currentTimeMillis()+expiration))
             .signWith(getSignKey(), SignatureAlgorithm.HS256)
             .compact();


    }

    public String extractUsername(String token) {
      return Jwts.parserBuilder()
              .setSigningKey(getSignKey())
              .build()
              .parseClaimsJws(token).getBody().getSubject();

    }
  public Boolean validateToken(String token) {

     try{
      Jwts.parserBuilder().setSigningKey(getSignKey())
              .build()
              .parseClaimsJws(token);
      return true;
            }
     catch(Exception e){
         System.out.println("Invalid Token"+e.getMessage());
         return false;

     }
  }
  public Boolean validateToken(String token, String userName) {
    return extractUsername(token).equals(userName)&&validateToken(token);
  }



}
