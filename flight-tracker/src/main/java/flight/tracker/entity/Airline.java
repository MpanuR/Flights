package flight.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Airline entity class
 * 
 */
@Entity
@Data
public class Airline {

    /**
     * Airline entity fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airlineCode;

    private String airlineName;
    private String airlinePhoneNumber;
    private String airlineWebsite;
    private String airlineCountry;

    /**
     * Airline entity one to many relationship with flight
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flight> flights = new HashSet<>();

}
