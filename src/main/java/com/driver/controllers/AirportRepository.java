package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    Map<String, Airport> airportMap = new TreeMap<>();
    Map<Integer, Flight> flightMap = new HashMap<>();
    Map<Integer,Passenger> passengerMap = new HashMap<>();
    Map<Integer,List<Integer>> flightPassengerMap = new HashMap<>();
    Map<Integer,Integer> passengerfareMap = new HashMap<>();
    Map<Integer,Integer> flightRevenueMap = new HashMap<>();
    public void addAirport(Airport airport)
    {
        airportMap.put(airport.getAirportName(),airport);
    }
    public List<Airport> getAllAirports()
    {
        return new ArrayList<>(airportMap.values());
    }
    public void addFlight(Flight flight)
    {
        flightMap.put(flight.getFlightId(),flight);
    }

    public void addPassenger(Passenger passenger)
    {
        passengerMap.put(passenger.getPassengerId(),passenger);
    }
    public List<Flight> getAllFlights()
    {
        return new ArrayList<>(flightMap.values());
    }
    public List<Integer> getPassengersFromFlightId(Integer flightId)
    {
        return flightPassengerMap.getOrDefault(flightId,new ArrayList<>());
    }
    public Integer getFlightCapacity(Integer flightId)
    {
       return flightMap.get(flightId).getMaxCapacity();
    }
    public void addFlightPassengerPair(Integer flightId,List<Integer>passenger)
    {
        flightPassengerMap.put(flightId,passenger);
    }
    public void addPassengerFarePair(Integer passengerId,Integer fare)
    {
        passengerfareMap.put(passengerId,fare);
    }
}
