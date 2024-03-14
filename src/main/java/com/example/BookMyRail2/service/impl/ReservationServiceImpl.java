package com.example.BookMyRail2.service.impl;

import com.example.BookMyRail2.entity.*;
import com.example.BookMyRail2.exception.CustomException;
import com.example.BookMyRail2.exception.ResourceNotFoundException;
import com.example.BookMyRail2.repository.*;
import com.example.BookMyRail2.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public Reservation createReservation(Reservation reservation) {

        User user = userRepository.findByUsername(reservation.getUser().getUsername()).orElseThrow(()-> new ResourceNotFoundException("User not found of username : "+reservation.getUser().getUsername()+" for reservation "));

        reservation.setUser(user);

        Train train = trainRepository.findById(reservation.getTrain().getId()).orElseThrow(()->
                new ResourceNotFoundException("Train not found with ID : "+reservation.getTrain().getId()+" for reservation "));

        if(reservation.getDepartureStation().getId()==reservation.getDestinationStation().getId())
        {
            throw new CustomException("Reservation destination & destination station cannot be same");
        }

        reservation.setBookingDate(LocalDate.now());
        reservation.setJourneyDate(train.getDepartureDate());

        Station departureStation =stationRepository.findById(reservation.getDepartureStation().getId()).orElseThrow(()->
                new ResourceNotFoundException("Departure station not found with ID : "+reservation.getDepartureStation().getId()+" for reservation"));

        Station destinationStation = stationRepository.findById(reservation.getDestinationStation().getId()).orElseThrow(()->
                new ResourceNotFoundException("Destination station not found with ID : "+reservation.getDestinationStation().getId()+" for reservation"));

        //this loop is for checking reservation destination Station is present in Train route(Segment) or not
        int checkDestStation=0;
        for(Segment segment : train.getSegmentList())
        {
            if(segment.getStopStation().getId()==reservation.getDestinationStation().getId())
            {
                checkDestStation++;
            }
        }
        if(!(checkDestStation>0))
        {
            throw new CustomException("Destination station must be in train route for reservation");
        }

        // this loop check reservation departure station is present in Train route(Segment) or not
        int checkDeptStation=0;
        int indexOfStartingloop = 0;
        for(Segment segment : train.getSegmentList())
        {
            if(segment.getStopStation().getId()==reservation.getDepartureStation().getId())
            {
                checkDeptStation++;
                indexOfStartingloop = train.getSegmentList().indexOf(segment);

            } else if (reservation.getDepartureStation().getId()==train.getDepartureStation().getId())
            {
                checkDeptStation++;
                indexOfStartingloop = 0;
            }
        }
        if(!(checkDeptStation>0))
        {
            throw new CustomException("Departure station must be in train route for reservation");
        }



        List<Passenger> passengerList = reservation.getPassengerList();

        Segment segment=null;

        for(Segment tempSegment : train.getSegmentList())
        {
            if(tempSegment.getStopStation().getId()==destinationStation.getId())
            {
                segment=tempSegment;
            }
        }


        long indexOfSegment = train.getSegmentList().indexOf(segment);

        for(Passenger passenger : passengerList)
        {

            for(int i = indexOfStartingloop ; i<=indexOfSegment; i++)
            {
                Segment tempSegment = train.getSegmentList().get(i);

                for(Seat seat : tempSegment.getSeatList())
                {
                    if(seat.getStatus().equals("vacant"))
                    {
                        seat.setStatus("booked");
                        seatRepository.save(seat);

                        if(segment.getId()==tempSegment.getId())
                        {
                            passenger.setSeat(seat);
                        }

                        passenger.setStatus("booked");

                        break;
                    }
                }
            }

            if(passenger.getSeat()==null)               // if all seats are booked it will go in waiting
            {
                passenger.setStatus("waiting");


            }
            passengerRepository.save(passenger);
        }



        //loop to count total travel distance // below code till end is for counting total travel distance
        List<Segment> trainSegments = train.getSegmentList();

        Segment departureStationSegment = null;
        int indexOfdepartureStationSegment=0;
        for(Segment tempSegment : trainSegments)
        {
            if(tempSegment.getStopStation().getId()==reservation.getDepartureStation().getId())
            {
                departureStationSegment = tempSegment;
                indexOfdepartureStationSegment = trainSegments.indexOf(tempSegment);
            }
        }

        long totalDistance=0;
        if(reservation.getDepartureStation().getId()==train.getDepartureStation().getId())
        {
            for(int i =indexOfdepartureStationSegment; i<=indexOfSegment;i++)
            {
                Segment tempSegment = train.getSegmentList().get(i);
                totalDistance=totalDistance+ tempSegment.getDistance();
            }
            reservation.setDistance(totalDistance);
        }
        else
        {
            for(int i =indexOfdepartureStationSegment+1; i<=indexOfSegment;i++)
            {
                Segment tempSegment = train.getSegmentList().get(i);
                totalDistance=totalDistance+ tempSegment.getDistance();
            }
            reservation.setDistance(totalDistance);
        }

        // now count the ticket price
        double ticketFare = (totalDistance*1.5)*(reservation.getPassengerList().size());
        reservation.setTicketFare(ticketFare);


        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsOfUser(long userid) {

        User user = userRepository.findById(userid).orElseThrow(()->
                new ResourceNotFoundException("User not found of ID : "+userid));

        List<Reservation> reservationList = reservationRepository.findAll();

        List<Reservation> reservationListOfUser = new ArrayList<>();

        for(Reservation reservation : reservationList)
        {
            if(reservation.getUser()==null)
            {
                continue;
            }
            else {
                if(reservation.getUser().getId()==userid)
                {
                    reservationListOfUser.add(reservation);
                }
            }

        }
        return reservationListOfUser;
    }
}
