package com.example.demo.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Token {
    String token;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getToken() {
        return token;
    }
}
