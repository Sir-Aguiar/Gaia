package com.example.gaia.service;

import com.example.gaia.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
  private static final long EXPIRATION_TIME = 1000 * (14 * (60 * 60 * 24));
  private static final String SECRET = "1208ash";

  public static String getUsernameFromToken(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
    return claims.getSubject();
  }

  public static boolean validateToken(String token, String username) {
    final String extractedUsername = getUsernameFromToken(token);
    return (extractedUsername.equals(username) && !isTokenExpired(token));
  }

  private static Date getExpirationDateFromToken(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
    return claims.getExpiration();
  }

  private static boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public static String generateSessionToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getUsername());

    Map<String, Object> tokenClaims = new HashMap<>();
    tokenClaims.put("permission", user.getPermission());
    tokenClaims.put("userId", user.getUserId());
    tokenClaims.put("name", user.getName());
    claims.putAll(tokenClaims);

    long now = System.currentTimeMillis();

    return Jwts.builder().setClaims(claims)
            .setIssuedAt(new Date(now)).setExpiration(new Date(now + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET).compact();
  }
}
