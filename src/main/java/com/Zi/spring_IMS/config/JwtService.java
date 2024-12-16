package com.Zi.spring_IMS.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Purpose:
//        Generate JWTs with user-specific data.
//        Validate and extract claims (e.g., username or expiration) from JWTs.
//        Sign JWTs with a secret key for integrity.
@Service
public class JwtService {
    //A hardcoded 256-bit secret key used for signing JWTs.
    // random key generator website: https://randomkeygen.com/
    private static final String SECRET_KEY = "ywssvWmBzHFrw9gAcAqc9A62VWNSgKrfDVpuRTalk6yICU5oU8HfYQwHxDErszbo"; //"CDA17228F1A9D4FE6C2E93D49EE35"

    public String extractUsername(String token) {
        //subject should be username
        return extractClaim(token, Claims::getSubject);
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //generate token with pure details
    public String generateToken(UserDetails userDetails) { return generateToken(new HashMap<>(), userDetails); }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        //ensures that the username embedded in the token
        // matches
        // the username of the authenticated user from the UserDetails object.
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) { return extractExpiration(token).before(new Date()); }

    public Date extractExpiration(String token) { return extractClaim(token, Claims::getExpiration); }

    //generate token with extract claims
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours later token expire
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token) // use Jws instead of jwt since we have signature
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
