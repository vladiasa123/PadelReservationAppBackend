package com.example.demo.Model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // JpaRepository already provides basic CRUD operations
}
