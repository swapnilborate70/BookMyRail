package com.example.BookMyRail2.controller;

import com.example.BookMyRail2.entity.Passenger;
import com.example.BookMyRail2.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PassengerController {


    @Autowired
    private PassengerService passengerService;

    @PutMapping("/cancel-booking")
    public ResponseEntity<List<Passenger>> cancelBooking(@RequestBody List<Passenger> passengerList)
    {
            return new ResponseEntity<>(passengerService.cancelBooking(passengerList), HttpStatus.OK);
    }


    @GetMapping("/all-passengers")
    public ResponseEntity<List<Passenger>> getAllPassengers()
    {
        return new ResponseEntity<>(passengerService.getAllPassengers(),HttpStatus.OK);
    }


}
