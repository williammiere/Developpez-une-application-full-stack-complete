package com.openclassrooms.mddapi.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * The JWT service.
 */
@Service
public class JwtService {

  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  /**
   * Extracts the username from a token.
   * 
   * @param token the token.
   * @return the username.
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a claim from a token.
   * 
   * @param token the token.
   * @param claimsResolver the claims resolver.
   * @return the claim.
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // Extracts all the data the token can give us
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims); 
  }

  /**
   * Generates a token.
   * 
   * @param username the username.
   * @return the token.
   */
  public String generateToken(String username) {
    return generateToken(new HashMap<>(), username);
  }

  /**
   * Generates a token.
   * 
   * @param extraClaims the extra claims.
   * @param username the username.
   * @return the token.
   */
  public String generateToken(Map<String, Object> extraClaims, String username) {
    return buildToken(extraClaims, username, jwtExpiration);
  }

  /**
   * Gets the expiration time.
   * 
   * @return the expiration time.
   */
  public long getExpirationTime() {
    return jwtExpiration;
  }

  /**
   * Builds a token.
   * 
   * @param extraClaims the extra claims.
   * @param username the username.
   * @param expiration the expiration.
   * @return the token.
   */
  public String buildToken(
    Map<String, Object> extraClaims,
    String username,
    long expiration
  ) {
    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(username)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + expiration))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  /**
   * Validates a token.
   * 
   * @param token the token.
   * @param userDetails the user details.
   * @return true if the token is valid, false otherwise.
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Checks if a token is expired.
   * 
   * @param token the token.
   * @return true if the token is expired, false otherwise.
   */
  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from a token.
   * 
   * @param token the token.
   * @return the expiration date.
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts all claims from a token.
   * 
   * @param token the token.
   * @return the claims.
   */
  public Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  /**
   * Gets the sign-in key.
   * 
   * @return the sign-in key.
   */
  public Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
