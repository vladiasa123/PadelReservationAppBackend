package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;

import java.util.Optional;

@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public MyAppUser createUser(@RequestBody MyAppUser user) {
        System.out.println("Received Signup Request: " + user.getUsername() + ", " + user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myAppUserRepository.save(user);
    }

    @PostMapping(value = "/req/login", consumes = "application/json")
    public ResponseEntity<?> loginUser(@RequestBody MyAppUser user) {
        System.out.println("Received Login Request: " + user.getEmail());

        Optional<MyAppUser> existingUser = myAppUserRepository.findByEmail(user.getEmail());

        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("User not found!" + user.getEmail()));
        }


        MyAppUser foundUser = existingUser.get();
        boolean passwordMatch = passwordEncoder.matches(user.getPassword(), foundUser.getPassword());

        if (!passwordMatch) {
            // Return a 401 Unauthorized with an error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials!"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(foundUser.getUsername(), user.getPassword())
        );
        return ResponseEntity.ok(new SuccesResponse("Login successful!"));
    }
}
