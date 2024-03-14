package com.example.BookMyRail2.service.impl;

import com.example.BookMyRail2.entity.Station;
import com.example.BookMyRail2.exception.ResourceNotFoundException;
import com.example.BookMyRail2.repository.StationRepository;
import com.example.BookMyRail2.service.StationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Override
    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public void deleteStation(long id) {

        Station station = stationRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Station not found of ID : "+id));

        stationRepository.deleteById(id);

    }

    @Override
    public Station getStation(long id) {
        Station station = stationRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Station not found of ID : "+id));

        return station;
    }

    @Override
    public List<Station> getAllStation() {
        return stationRepository.findAll();
    }

    @Override
    public Station updateStation(long id, Station updatedStation) {
        Station station = stationRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Station not for updating station"));

        BeanUtils.copyProperties(updatedStation, station, "id");
        return stationRepository.save(station);
    }
}
