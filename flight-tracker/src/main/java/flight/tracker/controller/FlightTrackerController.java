package flight.tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import flight.tracker.controller.model.FlightData;
import flight.tracker.controller.model.FlightData.FlightAirline;
import flight.tracker.controller.model.FlightData.FlightPassenger;
import flight.tracker.service.FlightTrackerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/flight")
@Slf4j
public class FlightTrackerController {

    @Autowired
    private FlightTrackerService flightTrackerService;

    /**
     * POST new flight to airline
     * 
     * @param airlineCode - PathVariable
     * @param flightData  - RequestBody
     * @return FlightData
     */
    @PostMapping("/{airlineCode}/flight")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightData createFlight(@PathVariable Long airlineCode, @RequestBody FlightData flightData) {
        log.info("Creating flight with data: {}", flightData.getFlightNumber());
        return flightTrackerService.createFlight(flightData, airlineCode);
    }

    /**
     * POST new airline
     * 
     * @param airline - RequestBody
     * @return FlightAirline
     */
    @PostMapping("/airline")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightAirline createAirline(@RequestBody FlightAirline airline) {
        log.info("Creating airline with data: {}", airline.getAirlineName());
        return flightTrackerService.createAirline(airline);
    }

    /**
     * POST new passenger to flight
     * 
     * @param passenger    - RequestBody
     * @param flightNumber - PathVariable
     * @return FlightPassenger
     */
    @PostMapping("/passenger/{flightNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightPassenger addPassenger(@RequestBody FlightPassenger passenger, @PathVariable Long flightNumber) {
        log.info("Adding passenger with data: {}", passenger.getPassengerFirstName(), flightNumber);
        return flightTrackerService.addPassenger(passenger, flightNumber);
    }

    /**
     * GET all flights
     * 
     * @return List<FlightData>
     */
    @GetMapping("/flightNumbers")
    public List<FlightData> listAllFlights() {
        log.info("Listing all flights");
        return flightTrackerService.retrieveAllFlights();
    }

    /**
     * GET flight by flight number
     * 
     * @param flightNumber
     * @return single Flight info
     */
    @GetMapping("/flight/{flightNumber}")
    public FlightData getFlight(@PathVariable Long flightNumber) {
        log.info("Retrieving flight with flight number: {}", flightNumber);
        return flightTrackerService.retrieveFlight(flightNumber);
    }

    /**
     * PUT for flight
     * 
     * @param airlineCode
     * @param flightNumber
     * @param flightData
     * @return updates flight info
     */
    @PutMapping("{airlineCode}/flight/{flightNumber}")
    public FlightData updateFlight(@PathVariable Long airlineCode, @PathVariable Long flightNumber,
            @RequestBody FlightData flightData) {

        flightData.setFlightNumber(flightNumber);
        log.info("Updating flight with flight number: {}", flightNumber);
        return flightTrackerService.createFlight(flightData, airlineCode);

    }

    /**
     * deletes flight by flight number
     * 
     * @param flightNumber
     * @return message confirming deletion of flight with provided flight number
     */
    @DeleteMapping("/flight/{flightNumber}")
    public Map<String, String> deleteFlightbyId(@PathVariable Long flightNumber) {
        log.info("Deleting flight with flight number: {}", flightNumber);
        flightTrackerService.deleteFlightById(flightNumber);
        return Map.of("message", "Deletion of flight with flight number: " + flightNumber + " was successful");

    }

    /**
     * deletes airline by airline code
     * 
     * @param airlineCode
     * @return message confirming deletion of airline with provided airline code
     */
    @DeleteMapping("/airline/{airlineCode}")
    public Map<String, String> deleteAirlinebyId(@PathVariable Long airlineCode) {
        log.info("Deleting airline with airline code: {}", airlineCode);
        flightTrackerService.deleteAirlineById(airlineCode);
        return Map.of("message", "Deletion of airline with airline code: " + airlineCode + " was successful");

    }

    /**
     * deletes passenger by passenger id
     * 
     * @param passengerId
     * @return message confirming deletion of passenger with provided passenger id
     */
    @DeleteMapping("/passenger/{passengerId}")
    public Map<String, String> deletePassengerbyId(@PathVariable Long passengerId) {
        log.info("Deleting passenger with passenger id: {}", passengerId);
        flightTrackerService.deletePassengerById(passengerId);
        return Map.of("message", "Deletion of passenger with passenger id: " + passengerId + " was successful");

    }

}
