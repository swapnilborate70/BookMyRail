package com.example.BookMyRail2.service.impl;

import com.example.BookMyRail2.entity.Seat;
import com.example.BookMyRail2.repository.SeatRepository;
import com.example.BookMyRail2.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;
    @Override
    public Seat createSeat(Seat seat) {
       return seatRepository.save(seat);
    }
}
