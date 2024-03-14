package com.example.BookMyRail2.service;

import com.example.BookMyRail2.entity.Station;

import java.util.List;

public interface StationService {

    public Station createStation(Station station);

    public void deleteStation(long id);

    public Station getStation(long id);

    public List<Station> getAllStation();

    public Station updateStation(long id, Station station);

}
