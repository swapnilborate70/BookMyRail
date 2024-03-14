package com.example.BookMyRail2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    private LocalDate bookingDate;


    private LocalDate journeyDate;

    @ManyToOne
    private Train train;

    @ManyToOne
    private Station departureStation;

    @ManyToOne
    private Station destinationStation;

    @OneToMany
    private List<Passenger> passengerList;

    private long distance;

    private double ticketFare;

}
