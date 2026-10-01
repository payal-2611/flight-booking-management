package jsp.springboot.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.springboot.entity.Flight;


public interface FlightRepository extends JpaRepository<Flight,Integer> {

	List<Flight> findByAirLine(String airLine);

	@Query("SELECT f FROM Flight f WHERE f.price BETWEEN :price1 AND :price2")
	List<Flight> findByPriceRange(Double price1, Double price2);

	@Query("SELECT f FROM Flight f where f.source=:source and f.destination=:destination order by f.price asc")
	List<Flight> findCheapestFlight(String source, String destination);

	List<Flight> findByAvailableSeatsGreaterThan(Integer availSeats);

	@Query("SELECT f FROM Flight f where f.source=:source and f.destination=:destination and f.departure between :start and :end")
	List<Flight> getFlightBySourceAndDestinationAndDeparture(String source, String destination,LocalDateTime start, LocalDateTime end);

}
