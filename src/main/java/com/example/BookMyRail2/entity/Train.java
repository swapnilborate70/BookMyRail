package com.example.BookMyRail2.entity;

import com.example.BookMyRail2.entity.Station;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private LocalDate departureDate;

    @ManyToOne
    private Station departureStation;

    private long totalSeats;

    @ManyToMany
    private List<Segment> segmentList;

}
