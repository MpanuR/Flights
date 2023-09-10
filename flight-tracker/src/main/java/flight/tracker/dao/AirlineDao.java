package flight.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import flight.tracker.entity.Airline;

/***
 * 
 * AirlineDao interface extends JpaRepository
 */
public interface AirlineDao extends JpaRepository<Airline, Long> {

}
