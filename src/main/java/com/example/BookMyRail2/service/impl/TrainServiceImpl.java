package com.example.BookMyRail2.service.impl;

import com.example.BookMyRail2.entity.Seat;
import com.example.BookMyRail2.entity.Segment;
import com.example.BookMyRail2.entity.Station;
import com.example.BookMyRail2.entity.Train;
import com.example.BookMyRail2.exception.CustomException;
import com.example.BookMyRail2.exception.ResourceNotFoundException;
import com.example.BookMyRail2.repository.SeatRepository;
import com.example.BookMyRail2.repository.SegmentRepository;
import com.example.BookMyRail2.repository.StationRepository;
import com.example.BookMyRail2.repository.TrainRepository;
import com.example.BookMyRail2.service.TrainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;


    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SegmentRepository segmentRepository;

    @Override
    public Train createTrain(Train train) {
        Station departureStation = stationRepository.findById(train.getDepartureStation().getId()).orElseThrow(()->
                new ResourceNotFoundException("Departure Station not found with ID : "+train.getDepartureStation().getId()));



       if(!(ChronoUnit.DAYS.between(LocalDate.now(),train.getDepartureDate())>=0))
        {
            throw new CustomException("Departure Date should be valid");
        }

       if(train.getSegmentList().isEmpty())
       {
           throw new CustomException("Segments should be given");
       }

       for(Segment segment : train.getSegmentList())
       {
           stationRepository.findById(segment.getStopStation().getId()).orElseThrow(()->
                   new ResourceNotFoundException("StopStation of Segment does not found"));

           List<Seat> segmentSeatList = new ArrayList<>();

           for(long i=0;i<train.getTotalSeats();i++)
           {
               Seat seat = new Seat();
               seat.setStatus("vacant");

               seatRepository.save(seat);

               segmentSeatList.add(seat);
           }

           segment.setSeatList(segmentSeatList);
           segmentRepository.save(segment);
       }
       return trainRepository.save(train);
    }

    @Override
    public List<Train> searchTrain(long pickUpStation, long destinationStation,LocalDate journeyDate) {
        List<Train> allTrains = trainRepository.findAll();
        List<Train> availableTrains = new ArrayList<>();


        if(allTrains.isEmpty())
        {
            return null;
        }
        else
        {
          Station pick = stationRepository.findById(pickUpStation).orElseThrow(()->
                  new ResourceNotFoundException("Provided PickUp Station not available to search train"));

          Station drop = stationRepository.findById(destinationStation).orElseThrow(()->
                  new ResourceNotFoundException("Provided Drop station not available to search train"));


          for(Train train : allTrains)
          {
              List<Station> tempTrainStation = new ArrayList<>();

              for(Segment tempSegment : train.getSegmentList())
              {
                  tempTrainStation.add(tempSegment.getStopStation());
              }

              if (tempTrainStation.contains(pick)&&tempTrainStation.contains(drop))
              {
                  availableTrains.add(train);
              } else if (tempTrainStation.contains(drop)&&train.getDepartureStation()==pick) {
                  availableTrains.add(train);

              }
          }
        }

        Iterator<Train> iterator = availableTrains.iterator();
        while (iterator.hasNext()) {
            Train train = iterator.next();
            long daysBetween = ChronoUnit.DAYS.between(train.getDepartureDate(), journeyDate);
            if (daysBetween != 0) {
                iterator.remove();
            }
        }
        return availableTrains;
    }

    @Override
    public void deleteTrain(long id) {

        Train train = trainRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Train not found for deleting with ID : "+id));

        trainRepository.deleteById(id);

    }

    @Override
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Override
    public Train updateTrain(Train updatedTrain, long id) {
        Train train = trainRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Train not found with ID : "+id));

        BeanUtils.copyProperties(updatedTrain,train,"id");
        return trainRepository.save(train);
    }
}
