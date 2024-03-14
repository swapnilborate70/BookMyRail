package com.example.BookMyRail2.service;

import com.example.BookMyRail2.entity.Reservation;

import java.util.List;

public interface ReservationService {

    public Reservation createReservation(Reservation reservation);

    public List<Reservation> getReservationsOfUser(long userid);


}
