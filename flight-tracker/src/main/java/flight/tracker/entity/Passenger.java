package flight.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Passenger {

    /**
     * Passenger entity fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;
    private String passengerSeatNumber;

    /**
     * Passenger entity many to many relationship with flight
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "passengers", cascade = CascadeType.PERSIST)
    private Set<Flight> flights = new HashSet<>();

}
