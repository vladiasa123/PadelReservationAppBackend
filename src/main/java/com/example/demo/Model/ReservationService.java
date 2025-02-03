package com.example.demo.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);
    }


    @Autowired
    private ReservationRepository availability;

    public List<Reservation> getAvailability(int dayId) {
        return availability.findByDayId(dayId);
    }


    public List<Reservation> findByUserId(int userId) {
        return availability.findByUserId(userId);
    }

}
