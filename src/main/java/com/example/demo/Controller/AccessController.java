package com.example.demo.Controller;


import com.example.demo.Model.Token;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class AccessController {


    @PostMapping(value = "req/accesPage", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> accessUser(@RequestBody Token token) throws Exception {
        System.out.println("Received token: " + token.getToken());

        var secret = "afskjhfbas7q23jfq80f12o0v8qh0if133b0v0810i3bv0138gh139hg10igb103gh103iasfas13qv11v13v13v13v1";

        try {
            var claims = Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token.getToken())
                    .getBody();
            System.out.println("Decoded claims: " + claims);
            Date expiration  = claims.getExpiration();
            boolean isExpired =  expiration.before(new Date());
            Map<String, String> response = new HashMap<>();
            response.put("token", token.getToken());
            response.put("isExpired", String.valueOf(isExpired));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("JWT verification failed: " + e.getMessage());
            throw new Exception("JWT token verification failed", e);
        }
    }
}

