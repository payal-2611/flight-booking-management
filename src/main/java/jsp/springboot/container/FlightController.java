package jsp.springboot.container;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.entity.Flight;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {

	@Autowired
	private FlightService flightService;

	// Insert a record
	// T save(T ref)
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> saveFlight(@RequestBody Flight flight) {
		return flightService.addFlight(flight);
	}

	// getAllFlights
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlight() {
		return flightService.getAllFlight();
	}

	// getFlightById
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> getByFlightId(@PathVariable Integer id) {
		return flightService.getById(id);
	}

	// get flight by source and destination and date
	@GetMapping("/source/{source}/destination/{destination}/date/{departure}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestinationAndDeparture(
			@PathVariable String source, @PathVariable String destination, @PathVariable LocalDate departure) {
		return flightService.getFlightBySourceAndDestinationAndDeparture(source, destination, departure);
	}

	// getFlightByAirline
	@GetMapping("/airLine/{airLine}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getByFlightAirLine(@PathVariable String airLine) {
		return flightService.getByAirLine(airLine);
	}

	// Update flight
	@PutMapping("/flight/{id}")
	public ResponseEntity<ResponseStructure<Flight>> getByFlightId(@PathVariable Integer id,
			@RequestBody Flight flight) {
		return flightService.updateFlight(id, flight);
	}

	// Delete flight
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> deleteFlightById(@PathVariable Integer id) {
		return flightService.deleteFlight(id);
	}

	// Find flight within price range
	@GetMapping("/range/{price1}/{price2}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightWithinPriceRange(@PathVariable Double price1,
			@PathVariable Double price2) {
		return flightService.getFlightWithinPriceRnage(price1, price2);
	}

	// get cheapest flight between two cities
	@GetMapping("/cheapest/{source}/{destination}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightBtwTwoCities(@PathVariable String source,
			@PathVariable String destination) {
		return flightService.getCheapestFlight(source, destination);
	}

	// find flight having more than X available seats
	@GetMapping("/availableSeats/{availSeats}")
	public ResponseEntity<ResponseStructure<List<Flight>>> findFlightHavingMoreSeats(@PathVariable Integer availSeats) {
		return flightService.findFlightHavingMoreSeats(availSeats);
	}

	// get flight by pagination and sorting
	@GetMapping("/page/{pageNumber}/size/{pageSize}/sort/{field}")
	public ResponseEntity<ResponseStructure<Page<Flight>>> getByPaginationAndSorting(@PathVariable int pageNumber,
			@PathVariable int pageSize, @PathVariable String field) {
		return flightService.getByPaginationAndSorting(pageNumber, pageSize, field);
	}
}
