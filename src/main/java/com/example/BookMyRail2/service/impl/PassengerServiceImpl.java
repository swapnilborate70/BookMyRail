package com.example.BookMyRail2.service.impl;

import com.example.BookMyRail2.entity.*;
import com.example.BookMyRail2.exception.CustomException;
import com.example.BookMyRail2.exception.ResourceNotFoundException;
import com.example.BookMyRail2.repository.*;
import com.example.BookMyRail2.service.PassengerService;
import com.example.BookMyRail2.service.ReservationService;
import com.example.BookMyRail2.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService
{

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private TrainRepository trainRepository;


    @Override
    public List<Passenger> cancelBooking(List<Passenger> passengerList) {

        for(Passenger passenger : passengerList)
        {
            Passenger completePassenger = passengerRepository.findById(passenger.getId()).orElseThrow(()->
                    new ResourceNotFoundException("Passenger not found with ID : "+passenger.getId()));

            List<Reservation> reservationList = reservationRepository.findAll();
            Reservation reservation = null;
            for(Reservation tempReservation : reservationList)
            {
                if(tempReservation.getPassengerList().contains(completePassenger))
                {
                    reservation = tempReservation;
                }
            }

            Train train = trainRepository.findById(reservation.getTrain().getId()).orElse(null);

            Station departureStation = reservation.getDepartureStation();

            Segment segment = null;
            int indexOfSegment = 0;
            int indexOfSeat = 0;
            int indexOfStartingLoop=0;
            Segment segmentOfDeparture =null;

            if(completePassenger.getStatus().equals("waiting"))
            {
                completePassenger.setStatus("cancelled");
                passengerRepository.save(completePassenger);
            }

            else if(completePassenger.getStatus().equals("booked"))
            {
                List<Segment> segmentListOfTrain = train.getSegmentList();
                for(Segment tempSegment : segmentListOfTrain)
                {
                    if(tempSegment.getStopStation().getId()==reservation.getDestinationStation().getId())
                    {
                        segment = tempSegment;
                        indexOfSegment = segmentListOfTrain.indexOf(tempSegment);
                        indexOfSeat = segment.getSeatList().indexOf(completePassenger.getSeat());



                        break;
                    }

                    if(tempSegment.getStopStation().getId()==reservation.getDepartureStation().getId())
                    {
                        segmentOfDeparture = tempSegment;
                        indexOfStartingLoop = segmentListOfTrain.indexOf(tempSegment);
                    }
                }

                completePassenger.setStatus("cancelled");
                completePassenger.setSeat(null);
                passengerRepository.save(completePassenger);

                for(int i=indexOfStartingLoop;i<=indexOfSegment;i++)
                {

                    Segment tempSegment = train.getSegmentList().get(i);

                    Seat seat = tempSegment.getSeatList().get(indexOfSeat);

                    seat.setStatus("vacant");

                    seatRepository.save(seat);

                    segmentRepository.save(tempSegment);

                }

            }

        }



        // below code is for allocating waiting passengers into booked.
        List<Reservation> reservationList =reservationRepository.findAll();

        for(Reservation reservation : reservationList)
        {
            List<Passenger> waitingPassengers = new ArrayList<>();

            for(Passenger tempPassenger : reservation.getPassengerList())
            {

                if(tempPassenger.getStatus().equals("waiting"))
                {
                    waitingPassengers.add(tempPassenger);
                }
            }

            if(!waitingPassengers.isEmpty())
            {
                for(Passenger tempPassenger : waitingPassengers) {

                    Train train = reservation.getTrain();

                    Segment segment = null;
                    int indexOfSegment = 0;
                    int indexOfSeat = 0;

                    for (Segment tempSegment : train.getSegmentList()) {
                        if (tempSegment.getStopStation().getId() == reservation.getDestinationStation().getId()) {
                            segment = tempSegment;
                            indexOfSegment = train.getSegmentList().indexOf(tempSegment);
                            break;
                        }
                    }

                    long countOfVacantSeats = 0;

                    for (Seat tempSeat : segment.getSeatList()) {
                        if (tempSeat.getStatus().equals("vacant")) {
                            indexOfSeat = segment.getSeatList().indexOf(tempSeat);
                            countOfVacantSeats++;
                            break;
                        }
                    }

                    if (countOfVacantSeats > 0) {

                        for (int i = 0; i <= indexOfSegment; i++) {

                            Segment tempSegment = train.getSegmentList().get(i);

                            Seat seat = tempSegment.getSeatList().get(indexOfSeat);

                            seat.setStatus("booked");

                            seatRepository.save(seat);


                            tempPassenger.setStatus("booked");
                            tempPassenger.setSeat(seat);

                            passengerRepository.save(tempPassenger);

                        }
                    }
                }
            }
        }

        return passengerList;
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }
}
