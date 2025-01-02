package com.example.demo;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


public class JwtUtil {
    private static final String SECRET = "afskjhfbas7q23jfq80f12o0v8qh0if133b0v0810i3bv0138gh139hg10igb103gh103iasfas13qv11v13v13v13v1";
    private static final long EXPIRATION_TIME = 864_000_000;
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
