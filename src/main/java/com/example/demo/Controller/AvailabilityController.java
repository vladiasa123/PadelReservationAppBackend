package com.example.demo.Controller;

import com.example.demo.Model.Reservation;
import com.example.demo.Model.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class AvailabilityController {

    @Autowired
    private ReservationService availabilityService;

    @PostMapping(value = "/req/availability", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> availability(@RequestBody Reservation reservation) throws Exception {
        var availableSlots = availabilityService.getAvailability(reservation.getDayId()).stream()
                .map(Reservation::getHour)
                .toList();

        Map<String, Object> response = new HashMap<>();

        if (availableSlots.isEmpty()) {
            response.put(false);
        } else {
            response.put("dayId", reservation.getDayId());
            response.put("availableSlots", availableSlots);
        }

        return ResponseEntity.ok(response);
    }
}
