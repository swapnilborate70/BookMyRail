package com.example.BookMyRail2.service;

import com.example.BookMyRail2.entity.Station;
import com.example.BookMyRail2.entity.Train;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {

    public Train createTrain(Train train);

    public List<Train> searchTrain(long pickUpStation, long destinationStation, LocalDate journeyDate);

    public void deleteTrain(long id);

    public List<Train> getAllTrains();

    public Train updateTrain(Train train, long id);

}
