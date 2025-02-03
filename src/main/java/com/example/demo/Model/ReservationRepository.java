package com.example.demo.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByDayId(int dayId);

    List<Reservation> findByUserId(int userId);



}
