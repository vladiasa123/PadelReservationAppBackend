package com.example.demo.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserAccountController {

    @GetMapping(value = "/req/userData")
    public ResponseEntity<String> getUserData(HttpSession session) {
        String username = (String) session.getAttribute("username");

        if(username != null){
            return ResponseEntity.ok("User data for: " + username);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

    }
}
