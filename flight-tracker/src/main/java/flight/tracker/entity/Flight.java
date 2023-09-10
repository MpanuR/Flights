package flight.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Flight entity class
 * 
 */
@Entity
@Data
public class Flight {

    /**
     * Flight entity fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightNumber;

    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;

    /**
     * Flight entity many to many relationship with passenger
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "flight_passenger", joinColumns = @JoinColumn(name = "flight_number"), inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    private Set<Passenger> passengers = new HashSet<>();

    /**
     * Flight entity many to one relationship with airline
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airline_code")
    private Airline airline;

}
