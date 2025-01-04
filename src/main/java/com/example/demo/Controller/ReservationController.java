package com.example.demo.Controller;

import com.example.demo.Model.Reservation;
import com.example.demo.Model.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;  // Inject the service here

    @PostMapping(value = "/req/Reservation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> reservationEntry(@RequestBody Reservation reservation) throws Exception {
        // Use the service to save the reservation
        Reservation savedReservation = reservationService.saveReservation(reservation);

        // Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Reservation successfully saved!");
        response.put("reservationId", String.valueOf(savedReservation.getData()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
