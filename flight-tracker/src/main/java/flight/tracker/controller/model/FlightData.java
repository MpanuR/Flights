package flight.tracker.controller.model;

import flight.tracker.entity.Passenger;
import lombok.Data;
import lombok.NoArgsConstructor;
import flight.tracker.entity.Flight;

import java.util.HashSet;
import java.util.Set;

import flight.tracker.entity.Airline;

@Data
@NoArgsConstructor
public class FlightData {

    private Long flightNumber;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private Long airlineCode;
    private Set<FlightPassenger> passengers = new HashSet<>();

    /**
     * FlightData constructor sets the flight data
     * 
     * @param flight
     */
    public FlightData(Flight flight) {
        this.flightNumber = flight.getFlightNumber();
        this.departureDate = flight.getDepartureDate();
        this.departureTime = flight.getDepartureTime();
        this.arrivalDate = flight.getArrivalDate();
        this.arrivalTime = flight.getArrivalTime();
        this.airlineCode = flight.getAirline().getAirlineCode();

        for (Passenger passenger : flight.getPassengers()) {
            this.passengers.add(new FlightPassenger(passenger));
        }

    }

    @Data
    @NoArgsConstructor
    public static class FlightPassenger {

        private Long passengerId;
        private String passengerFirstName;
        private String passengerLastName;
        private String passengerEmail;
        private String passengerSeatNumber;

        /**
         * FlightPassenger constructor sets the passenger data
         * 
         * @param passenger
         */
        public FlightPassenger(Passenger passenger) {
            passengerId = passenger.getPassengerId();
            passengerFirstName = passenger.getPassengerFirstName();
            passengerLastName = passenger.getPassengerLastName();
            passengerEmail = passenger.getPassengerEmail();
            passengerSeatNumber = passenger.getPassengerSeatNumber();

        }

    }

    @Data
    @NoArgsConstructor
    public static class FlightAirline {

        private Long airlineCode;
        private String airlineName;
        private String airlinePhoneNumber;
        private String airlineWebsite;
        private String airlineCountry;

        private Set<Flight> flights = new HashSet<>();

        /**
         * FlightAirline constructor sets the airline data
         * 
         * @param airline
         */
        public FlightAirline(Airline airline) {
            this.airlineCode = airline.getAirlineCode();
            this.airlineName = airline.getAirlineName();
            this.airlinePhoneNumber = airline.getAirlinePhoneNumber();
            this.airlineWebsite = airline.getAirlineWebsite();
            this.airlineCountry = airline.getAirlineCountry();

        }

    }

}
