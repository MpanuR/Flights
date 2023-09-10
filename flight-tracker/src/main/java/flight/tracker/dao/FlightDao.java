package flight.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import flight.tracker.entity.Flight;

/***
 * 
 * FlightDao interface extends JpaRepository
 */
public interface FlightDao extends JpaRepository<Flight, Long> {

}
