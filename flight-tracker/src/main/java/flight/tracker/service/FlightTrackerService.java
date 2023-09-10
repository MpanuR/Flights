package flight.tracker.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flight.tracker.controller.model.FlightData;
import flight.tracker.controller.model.FlightData.FlightAirline;
import flight.tracker.controller.model.FlightData.FlightPassenger;
import flight.tracker.dao.AirlineDao;
import flight.tracker.dao.FlightDao;
import flight.tracker.dao.PassengerDao;
import flight.tracker.entity.Airline;
import flight.tracker.entity.Flight;
import flight.tracker.entity.Passenger;

import org.springframework.transaction.annotation.Transactional;

/***
 * Service class for FlightTracker
 * 
 */
@Service
public class FlightTrackerService {

    @Autowired
    private FlightDao flightDao;

    /**
     * Creates & saves new flight to airline
     * 
     * @param flightData
     * @param airlineCode
     * @return new FlightData in FlightDao
     */
    @Transactional(readOnly = false)
    public FlightData createFlight(FlightData flightData, Long airlineCode) {
        Airline airline = findAirlineByAirlineCode(airlineCode);
        Flight flight = findOrCreateFlight(airlineCode, flightData.getFlightNumber());

        flight.setAirline(airline);
        copyFlightsFields(flight, flightData);

        airline.getFlights().add(flight);

        return new FlightData(flightDao.save(flight));
    }

    /**
     * Copies flight data to flight
     * 
     * @param flight
     * @param flightData
     */
    private void copyFlightsFields(Flight flight, FlightData flightData) {
        flight.setFlightNumber(flightData.getFlightNumber());
        flight.setDepartureDate(flightData.getDepartureDate());
        flight.setDepartureTime(flightData.getDepartureTime());
        flight.setArrivalDate(flightData.getArrivalDate());
        flight.setArrivalTime(flightData.getArrivalTime());

    }

    /**
     * Finds or creates flight
     * 
     * @param airlineCode
     * @param flightNumber
     * @return flight
     */
    private Flight findOrCreateFlight(Long airlineCode, Long flightNumber) {
        Flight flight;

        if (Objects.isNull(flightNumber)) {
            flight = new Flight();
        } else {
            flight = findFlightByFlightNumber(airlineCode, flightNumber);
        }

        return flight;
    }

    /**
     * Finds flight by flight number or throws exception
     * 
     * @param airlineCode
     * @param flightNumber
     * @return flight
     */
    private Flight findFlightByFlightNumber(Long airlineCode, Long flightNumber) {
        Flight flight = flightDao.findById(flightNumber).orElseThrow(() -> new NoSuchElementException(
                "FlightNumber not found: " + flightNumber));

        if (flight.getAirline().getAirlineCode() != airlineCode) {
            throw new IllegalArgumentException(
                    "FlightNumber not found: " + flightNumber + " for airlineCode: " + airlineCode + "");
        }

        return flight;
    }

    /**
     * Find flight by flight number using Dao or throw exception
     * 
     * @param flightNumber
     * @return
     */
    @Transactional(readOnly = true)
    private Flight findFlightbyFlightNumber(Long flightNumber) {
        return flightDao.findById(flightNumber)
                .orElseThrow(
                        () -> new NoSuchElementException("Flight with flightNumber=" + flightNumber + "not found."));
    }

    /**
     * Retrieves all flights
     * 
     * @return list of all flights
     */
    @Transactional(readOnly = true)
    public List<FlightData> retrieveAllFlights() {

        return flightDao.findAll().stream().map(FlightData::new).toList();

    }

    @Autowired
    private AirlineDao airlineDao;

    /**
     * Creates & saves new airline
     * 
     * @param flightAirline
     * @return new FlightAirline in AirlineDao
     */
    @Transactional(readOnly = false)
    public FlightAirline createAirline(FlightAirline flightAirline) {
        Long airlineCode = flightAirline.getAirlineCode();
        Airline airline = findOrCreateAirline(airlineCode);
        copyAirlineFields(airline, flightAirline);

        Airline dbAirline = airlineDao.save(airline);
        return new FlightAirline(dbAirline);

    }

    /**
     * Copies airline data to airline
     * 
     * @param airline
     * @param flightAirline
     */
    private void copyAirlineFields(Airline airline, FlightAirline flightAirline) {
        airline.setAirlineCode(flightAirline.getAirlineCode());
        airline.setAirlineName(flightAirline.getAirlineName());
        airline.setAirlinePhoneNumber(flightAirline.getAirlinePhoneNumber());
        airline.setAirlineWebsite(flightAirline.getAirlineWebsite());
        airline.setAirlineCountry(flightAirline.getAirlineCountry());
    }

    /**
     * Finds or creates airline
     * 
     * @param airlineCode
     * @return airline
     */
    private Airline findOrCreateAirline(Long airlineCode) {

        Airline airline;

        if (Objects.isNull(airlineCode)) {
            airline = new Airline();
        } else {

            airline = findAirlineByAirlineCode(airlineCode);
        }

        return airline;

    }

    /**
     * Finds airline by airline code or throws exception
     * 
     * @param airlineCode
     * @return airline
     */
    private Airline findAirlineByAirlineCode(Long airlineCode) {
        return airlineDao.findById(airlineCode).orElseThrow(() -> new NoSuchElementException(
                "AirlineCode not found: " + airlineCode));
    }

    @Autowired
    private PassengerDao passengerDao;

    /**
     * Adds passenger to flight
     * 
     * @param flightPassenger
     * @param flightNumber
     * @return new FlightPassenger in PassengerDao
     */
    @Transactional(readOnly = false)
    public FlightPassenger addPassenger(FlightPassenger flightPassenger, Long flightNumber) {
        Flight flight = findFlightbyFlightNumber(flightNumber);
        Passenger passenger = findOrCreatePassenger(flightPassenger.getPassengerId());

        copyPassengerFields(passenger, flightPassenger);
        passenger.getFlights().add(flight);
        flight.getPassengers().add(passenger);

        return new FlightPassenger(passengerDao.save(passenger));

    }

    /**
     * Copies passenger data to passenger
     * 
     * @param passenger
     * @param flightPassenger
     */
    private void copyPassengerFields(Passenger passenger, FlightPassenger flightPassenger) {
        passenger.setPassengerId(flightPassenger.getPassengerId());
        passenger.setPassengerFirstName(flightPassenger.getPassengerFirstName());
        passenger.setPassengerLastName(flightPassenger.getPassengerLastName());
        passenger.setPassengerEmail(flightPassenger.getPassengerEmail());
        passenger.setPassengerSeatNumber(flightPassenger.getPassengerSeatNumber());

    }

    /**
     * Finds or creates passenger
     * 
     * @param passengerId
     * @return passenger
     */
    private Passenger findOrCreatePassenger(Long passengerId) {
        Passenger passenger;

        if (Objects.isNull(passengerId)) {
            passenger = new Passenger();
        } else {
            passenger = findPassengerByPassengerId(passengerId);
        }

        return passenger;
    }

    /**
     * Finds passenger by passenger id or throws exception
     * 
     * @param passengerId
     * @return passenger
     */
    @Transactional(readOnly = true)
    private Passenger findPassengerByPassengerId(Long passengerId) {
        return passengerDao.findById(passengerId)
                .orElseThrow(
                        () -> new NoSuchElementException("Passenger with passengerId=" + passengerId + "not found."));
    }

    /**
     * Retrieves flight by flight number
     * 
     * @param flightNumber
     * @return flight
     */
    @Transactional(readOnly = true)
    public FlightData retrieveFlight(Long flightNumber) {
        Flight flight = findFlightbyFlightNumber(flightNumber);
        return new FlightData(flight);
    }

    /**
     * Deletes flight by flight number
     * 
     * @param flightNumber
     */
    @Transactional(readOnly = false)
    public void deleteFlightById(Long flightNumber) {
        Flight flight = findFlightbyFlightNumber(flightNumber);
        flightDao.delete(flight);
    }

    /**
     * Deletes airline by airline code
     * 
     * @param airlineCode
     */
    @Transactional(readOnly = false)
    public void deleteAirlineById(Long airlineCode) {
        Airline airline = findAirlineByAirlineCode(airlineCode);
        airlineDao.delete(airline);
    }

    /**
     * Deletes passenger by passenger id
     * 
     * @param passengerId
     */
    @Transactional(readOnly = false)
    public void deletePassengerById(Long passengerId) {
        Passenger passenger = findPassengerByPassengerId(passengerId);
        passengerDao.delete(passenger);
    }

}
