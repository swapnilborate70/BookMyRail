package com.example.BookMyRail2.controller;

import com.example.BookMyRail2.entity.Reservation;
import com.example.BookMyRail2.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController
{


    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation)
    {
        return new ResponseEntity<>(reservationService.createReservation(reservation), HttpStatus.CREATED);
    }


    @GetMapping ("/reservations/user/{userid}")
    public ResponseEntity<List<Reservation>> getReservationsOfUser(@PathVariable long userid)
    {
            return new ResponseEntity<>(reservationService.getReservationsOfUser(userid),HttpStatus.OK);
    }


}
