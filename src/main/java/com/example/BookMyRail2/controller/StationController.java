package com.example.BookMyRail2.controller;

import com.example.BookMyRail2.entity.Station;
import com.example.BookMyRail2.response.ApiResponse;
import com.example.BookMyRail2.service.StationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StationController {

    @Autowired
    private StationService stationService;


    @PostMapping("/station")
    public ResponseEntity<Station> createStation(@Valid @RequestBody Station station)
    {
        System.out.println("");
        return new ResponseEntity<>(stationService.createStation(station), HttpStatus.CREATED);
    }

    @DeleteMapping("/station/{id}")
    public ResponseEntity<ApiResponse> deleteStation(@PathVariable long id)
    {
        stationService.deleteStation(id);
        return new ResponseEntity<>(new ApiResponse("success","Station deleted of ID : "+id),HttpStatus.OK);
    }

    @GetMapping("/station/{id}")
    public ResponseEntity<Station> getStation(@PathVariable long id)
    {
            return new ResponseEntity<>(stationService.getStation(id),HttpStatus.FOUND);
    }

    @GetMapping("/stations")
    public List<Station> getAllStation()
    {
            return stationService.getAllStation();
    }

    @PutMapping("/station/{id}")
    public ResponseEntity<Station> updateStation(@RequestBody Station station, @PathVariable long id)
    {
        return new ResponseEntity<>(stationService.updateStation(id,station),HttpStatus.OK);
    }


}
