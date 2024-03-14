package com.example.BookMyRail2.controller;

import com.example.BookMyRail2.entity.Segment;
import com.example.BookMyRail2.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SegmentController {


    @Autowired
    private SegmentService segmentService;

    @GetMapping("/available-seats")
    public ResponseEntity<Long> availableSeats(@RequestBody Segment segment)
    {
        return new ResponseEntity<>(segmentService.availableSeats(segment), HttpStatus.OK);
    }



}

