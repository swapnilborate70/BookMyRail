package com.example.BookMyRail2.controller;

import com.example.BookMyRail2.entity.Station;
import com.example.BookMyRail2.entity.Train;
import com.example.BookMyRail2.response.ApiResponse;
import com.example.BookMyRail2.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/train")
    public ResponseEntity<Train> createTrain(@Valid @RequestBody Train train)
    {
        return new ResponseEntity<>(trainService.createTrain(train), HttpStatus.CREATED);
    }

    @GetMapping("/search-trains")
    public ResponseEntity<List<Train>> getTrain(@RequestParam long pick, @RequestParam long drop, @RequestParam LocalDate journeyDate)
    {
        return new ResponseEntity<>(trainService.searchTrain(pick,drop,journeyDate),HttpStatus.OK);
    }


    @DeleteMapping("/train/{id}")
    public ResponseEntity<ApiResponse> deleteTrain(@PathVariable long id)
    {
        trainService.deleteTrain(id);
        return new ResponseEntity<>(new ApiResponse("success","Train record deleted of ID : "+id),HttpStatus.OK);
    }

    @GetMapping("/trains")
    public ResponseEntity<List<Train>> getAllTrains()
    {
        return new ResponseEntity<>(trainService.getAllTrains(),HttpStatus.OK);
    }

    @PutMapping("/train/{id}")
    public ResponseEntity<Train> updateTrain(@RequestBody Train train , @PathVariable long id)
    {
        return new ResponseEntity<>(trainService.updateTrain(train,id),HttpStatus.OK);
    }

}
