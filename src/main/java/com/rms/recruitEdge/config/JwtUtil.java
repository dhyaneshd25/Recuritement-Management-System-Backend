package com.rms.recruitEdge.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String accessSecret;

    @Value("${jwt.expiration}")
    private long accessExpiration; // e.g. 15 * 60 * 1000 (15 min)

    @Value("${jwt.refresh-secret}")
    private String refreshSecret;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration; // e.g. 7 * 24 * 60 * 60 * 1000 (7 days)

    private Key accessKey() {
        return Keys.hmacShaKeyFor(accessSecret.getBytes());
    }

    private Key refreshKey() {
        return Keys.hmacShaKeyFor(refreshSecret.getBytes());
    }

    // ---------------- ACCESS TOKEN ----------------

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(accessKey())
                .compact();
    }

    public String extractEmail(String token) {
        return parseClaims(token, accessKey()).getSubject();
    }

    /** Returns true only if valid AND not expired. Does not throw. */
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token, accessKey());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ---------------- REFRESH TOKEN ----------------

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(refreshKey())
                .compact();
    }

    public String extractEmailFromRefreshToken(String token) {
        return parseClaims(token, refreshKey()).getSubject();
    }

    public boolean isRefreshTokenValid(String token) {
        try {
            parseClaims(token, refreshKey());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ---------------- SHARED ----------------

    /** Throws ExpiredJwtException / JwtException — let the filter/service handle those. */
    private Claims parseClaims(String token, Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}




// package com.rms.recruitEdge.config;

// import java.security.Key;
// import java.util.Date;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;

// @Service
// public class JwtUtil {

//     @Value("${jwt.secret}")
//     private String secret;

//     @Value("${jwt.expiration}")
//     private long expiration;

//     private Key getSigningKey() {
//         return Keys.hmacShaKeyFor(secret.getBytes());
//     }

//     public String generateToken(String email) {
//         return Jwts.builder()
//                 .setSubject(email)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                 // .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
//                 .signWith(getSigningKey())
//                 .compact();
//     }

//     public String extractEmail(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(getSigningKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }

//     public boolean isTokenValid(String token) {
//         try {
//             Jwts.parserBuilder()
//                     .setSigningKey(getSigningKey())
//                     .build()
//                     .parseClaimsJws(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }
// }