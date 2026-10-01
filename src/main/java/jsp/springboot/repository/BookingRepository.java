package jsp.springboot.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.entity.Booking;
import jsp.springboot.enums.Status;

public interface BookingRepository extends JpaRepository<Booking ,Integer> {

	List<Booking> findByFlight_FlightId(Integer flightId);

	List<Booking> findByBookingDateTimeBetween(LocalDateTime start, LocalDateTime end);

	List<Booking> findByStatus(Status status);

	List<Booking> findByPassengersPassengerId(Integer passengerId);
}
