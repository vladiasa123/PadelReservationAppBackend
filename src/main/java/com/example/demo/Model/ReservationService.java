package com.example.demo.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;  // <-- Import the correct annotation

@Service  // Marks this as a Spring-managed service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;  // This is no longer static

    // Instance method to save reservation
    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);  // Save the reservation to the database
    }
}
