package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepository;



    public void addAirport(Airport airport)
    {
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName()
    {
        List<Airport> allAirports = airportRepository.getAllAirports();
        int maxTerminal = -1;
        String nameOfAirport = "";

        for(Airport airport : allAirports)
        {
            if(maxTerminal < airport.getNoOfTerminals())
            {
                maxTerminal = airport.getNoOfTerminals();
                nameOfAirport = airport.getAirportName();
            }
        }
        return nameOfAirport;
    }
    public void addFlight(Flight flight)
    {
        airportRepository.addFlight(flight);
    }
    public void addPassenger(Passenger passenger)
    {
        airportRepository.addPassenger(passenger);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity)
    {
        List<Flight> flightList = airportRepository.getAllFlights();
        double duration = Integer.MAX_VALUE;

        for(Flight flight : flightList)
        {
            if(fromCity.equals(flight.getFromCity()) && toCity.equals(flight.getToCity()) && duration > flight.getDuration())
            {
                duration = flight.getDuration();
            }
        }
        return duration;
    }

    public String bookATicket(Integer flightId,Integer passengerId)
    {
       List<Integer> passengerList = airportRepository.getPassengersFromFlightId(flightId);
       if(passengerList.size() < airportRepository.getFlightCapacity(flightId) && ! passengerList.contains(passengerId))
       {
           passengerList.add(passengerId);
           airportRepository.addFlightPassengerPair(flightId,passengerList);
           Integer fare = 3000 + 50*passengerList.size()-1;
           airportRepository.addPassengerFarePair(passengerId,fare);
           return "SUCCESS";
       }
       return "FAILURE";
    }
    public String cancelATicket(Integer flightId,Integer passengerId)
    {
        List<Integer> passengerList = airportRepository.getPassengersFromFlightId(flightId);
        if(passengerList.contains(passengerId))
        {
            passengerList.remove(passengerId);
            airportRepository.addFlightPassengerPair(flightId,passengerList);
            airportRepository.passengerfareMap.remove(passengerId);
            return "SUCCESS";
        }
        return "FAILURE";
    }
    public int getNumberOfPeople(Date date,String airportName)
    {
        int cnt = 0;
        List<Flight> flightList = airportRepository.getAllFlights();
        for(Flight flight : flightList)
        {
            if(flight.getFlightDate().compareTo(date) == 0 &&(airportName.equals(flight.getFromCity()) || airportName.equals(flight.getToCity())))
            {
                Integer flightId = flight.getFlightId();
                cnt += airportRepository.getPassengersFromFlightId(flightId).size();
            }
        }
        return cnt;
    }
    public int calculateFare(Integer flightId)
    {
        int passengersWhoBooked = airportRepository.getPassengersFromFlightId(flightId).size();
        int fare = 3000 + (passengersWhoBooked*50);
        return fare;
    }
    public int totalRevenue(Integer flightId)
    {
        List<Integer> revenueList = airportRepository.flightPassengerMap.get(flightId);
        Integer totalFare = 0;

        for(Integer fares : revenueList)
        {
            totalFare += airportRepository.passengerfareMap.get(fares);
        }
        return totalFare;
    }

}
