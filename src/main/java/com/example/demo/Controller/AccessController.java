package com.example.demo.Controller;


import com.example.demo.Model.Token;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

@RestController
public class AccessController {


    @PostMapping(value = "req/accesPage", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> accessUser(@RequestBody Token token) throws Exception {
        System.out.println("Received token: " + token.getToken());

        var secret = "afskjhfbas7q23jfq80f12o0v8qh0if133b0v0810i3bv0138gh139hg10igb103gh103iasfas13qv11v13v13v13v1";

        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token.getToken());
        } catch (Exception e) {
            System.err.println("JWT verification failed: " + e.getMessage());
            throw new Exception("JWT token verification failed", e);
        }


        return ResponseEntity.ok(Map.of("token", token.getToken()));
    }
    }

