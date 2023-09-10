package flight.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import flight.tracker.entity.Passenger;

/***
 * 
 * PassengerDao interface extends JpaRepository
 */
public interface PassengerDao extends JpaRepository<Passenger, Long> {

}
