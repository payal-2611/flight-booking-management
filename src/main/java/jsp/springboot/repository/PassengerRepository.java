package jsp.springboot.repository;

import jsp.springboot.entity.Passenger;
import jsp.springboot.enums.Gender;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PassengerRepository extends JpaRepository<Passenger, Integer>{

	Optional<Passenger> findByContactNumber(long contactNumber);

	List<Passenger> findByGender(Gender gender);

	@Query("select p from Passenger p WHERE p.booking.flight.flightId = :flightId")
	List<Passenger> findPassengersByFlight(@Param("flightId") Integer flightId);
}
