package com.example.BookMyRail2.service.impl;

import com.example.BookMyRail2.entity.Seat;
import com.example.BookMyRail2.entity.Segment;
import com.example.BookMyRail2.exception.ResourceNotFoundException;
import com.example.BookMyRail2.repository.SegmentRepository;
import com.example.BookMyRail2.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SegmentServiceImpl implements SegmentService {

    @Autowired
    private SegmentRepository segmentRepository;

    @Override
    public long availableSeats(Segment segment) {

        Segment tempSegment = segmentRepository.findById(segment.getId()).orElseThrow(()->
                new ResourceNotFoundException("Segment not found for finding available seats"));

         List<Seat> tempSegmentSeatList = tempSegment.getSeatList();

        List<Seat> availableSeats = new ArrayList<>();

         for(Seat seat : tempSegmentSeatList)
         {
             if(seat.getStatus().equals("vacant"))
             {
                 availableSeats.add(seat);
             }
         }
         return availableSeats.size();
    }
}
