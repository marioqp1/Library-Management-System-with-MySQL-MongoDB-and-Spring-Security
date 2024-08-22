package com.fuinco.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    private final long jwtExpirationInMs; // 3M
    private final String secretKey;

    public JwtService() {
        secretKey = "WQzX+qet+e0vCZBrZaXgCgCIfEPdI3Inb4368Aht/Ls=";
        jwtExpirationInMs = 1000 * 60 * 60 * 24;
    }

//    public String generateSecretKey() {
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey secretKey = keyGen.generateKey();
//            String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//            System.out.println("key: " + key);
//            return key;
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error generating secret key", e);
//        }
//    }

    public String generateToken(String userName, int id) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("UserId", id);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        String token = Jwts.builder().addClaims(claims).setSubject(userName).setIssuedAt(now).setExpiration(expiryDate).signWith(getKey(), SignatureAlgorithm.HS256).compact();
        System.out.println("Generated JWT Token: " + token); // Debug log
        return token;
    }


    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        System.out.println(Arrays.toString(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public int extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return (int) claims.get("userId");
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
