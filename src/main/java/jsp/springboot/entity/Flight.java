package jsp.springboot.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer flightId;
	
	@Column(nullable=false)
	private String airLine;
	
	@Column(nullable=false)
	private String source;
	
	@Column(nullable=false)
	private String destination;
	
	@Column(nullable=false)
	private LocalDateTime departure;
	
	@Column(nullable=false)
	private LocalDateTime arrival;
	
	@Column(nullable=false)
	private Integer totalSeats;
	
	@Column(nullable=false)
	private Integer availableSeats;
	
	@Column(nullable=false)
	private Double price;
	
	@OneToMany(mappedBy="flight")
	@JsonIgnore
	private List<Booking> bookings;
}
