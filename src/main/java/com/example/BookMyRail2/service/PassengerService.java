package com.example.BookMyRail2.service;

import com.example.BookMyRail2.entity.Passenger;

import java.util.List;

public interface PassengerService {


    public List<Passenger> cancelBooking(List<Passenger> passengerList);

    public List<Passenger> getAllPassengers();

}
