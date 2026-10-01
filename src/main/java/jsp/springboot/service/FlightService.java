package jsp.springboot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jsp.springboot.exception.NoRecordAvailableException;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Flight;
import jsp.springboot.repository.FlightRepository;


@Service
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;
	
	//add flights
	public ResponseEntity<ResponseStructure<Flight>> addFlight(Flight flight) {
		
		Flight savedFlight=flightRepository.save(flight);
		
		ResponseStructure<Flight> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Created Successfully!");
		response.setData(savedFlight);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	//Get All Flights
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlight() {
		
		List<Flight> flights=flightRepository.findAll();
		
		if(flights.isEmpty()) {
	        throw new NoRecordAvailableException("No Flight Records Available");
		}
		
		ResponseStructure<List<Flight>> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("All Flight Record Found Successfully!");
		response.setData(flights);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	//Get ById
	public ResponseEntity<ResponseStructure<Flight>> getById(Integer id) {
		
		Optional<Flight> op=flightRepository.findById(id);

		if(op.isEmpty()) {
	        throw new NoRecordAvailableException("No Flight Records Available for id :"+id);
		}
		
		ResponseStructure<Flight> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Record Found Successfully!");
		response.setData(op.get());
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	//GET FLIGHT BY SOURCE AND DESTINATION AND DATE
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestinationAndDeparture(String source,
				String destination, LocalDate departure) {
		
		LocalDateTime start = departure.atStartOfDay();
		LocalDateTime end = departure.atTime(LocalTime.MAX);
			
		List<Flight> flights =flightRepository.getFlightBySourceAndDestinationAndDeparture(source,destination,start, end);
		
		if(flights.isEmpty())
			throw new NoRecordAvailableException("No record available");

		ResponseStructure<List<Flight>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Fetched all record Successfully!");
		res.setData(flights);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	//get flight by airline
	public ResponseEntity<ResponseStructure<List<Flight>>> getByAirLine(String airLine) {
		
		List<Flight> flights=flightRepository.findByAirLine(airLine);

		if(flights.isEmpty()) {
	        throw new NoRecordAvailableException("No Flight Records Available for id :"+airLine);
		}
		
		ResponseStructure<List<Flight>> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Record Found Successfully for "+airLine);
		response.setData(flights);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	//Update Flight
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(Integer id, Flight flight) {
		
		Optional<Flight> opt =flightRepository.findById(id);
		
		if(opt.isEmpty()) {
	        throw new NoRecordAvailableException("No Flight Records Available");
		}
		
		Flight availFlight=opt.get();
		
		availFlight.setAirLine(flight.getAirLine());
		availFlight.setSource(flight.getSource());
		availFlight.setDestination(flight.getDestination());
		availFlight.setDeparture(flight.getDeparture());
		availFlight.setArrival(flight.getArrival());
		availFlight.setTotalSeats(flight.getTotalSeats());
		
		Flight updatedFlight=flightRepository.save(availFlight);
		
		ResponseStructure<Flight> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Record Updated Successfully!");
		response.setData(updatedFlight);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	//Delete flight
	public ResponseEntity<ResponseStructure<Flight>> deleteFlight(Integer id) { 
		
		Optional<Flight> op=flightRepository.findById(id);

		if(op.isEmpty()) {
	        throw new NoRecordAvailableException("No Flight Records Available for id :"+id);
		}
		
		Flight flight=op.get();
		
		flightRepository.deleteById(id);
		
		ResponseStructure<Flight> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Flight Record Delete Successfully!");
		response.setData(flight);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	//find flight within price range
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightWithinPriceRnage(Double price1, Double price2) {
		
		List<Flight> flights=flightRepository.findByPriceRange(price1,price2);
		
		if(flights.isEmpty()) {
	        throw new NoRecordAvailableException("No Flights Records Available within this range");
		}
		
		ResponseStructure<List<Flight>> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Record Found withing range"+price1+" To "+price2);
		response.setData(flights);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	//find cheapest flight between two cities
	public ResponseEntity<ResponseStructure<Flight>> getCheapestFlight(String source, String destination) {

		List<Flight> flights=flightRepository.findCheapestFlight(source,destination);
		
		if(flights.isEmpty()) {
	        throw new NoRecordAvailableException("No Flights Available from "+source+" to "+destination);
		}
				
		ResponseStructure<Flight> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Cheapest Flight Found from "+source+" To "+destination);
		response.setData(flights.get(0));
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<Flight>>> findFlightHavingMoreSeats(Integer availSeats) {

		List<Flight> flights=flightRepository.findByAvailableSeatsGreaterThan(availSeats);
		
		if(flights.isEmpty()) {
	        throw new NoRecordAvailableException("No Flights are Available");
		}
		
		ResponseStructure<List<Flight>> response=new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flights having more than " + availSeats + " available seats");
		response.setData(flights);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	//find by pagination and sorting
	public ResponseEntity<ResponseStructure<Page<Flight>>> getByPaginationAndSorting(int pageNumber, int pageSize,String field) {
		
	     PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,Sort.by(field).ascending());

	     Page<Flight> flights = flightRepository.findAll(pageRequest);

	    if (flights.isEmpty()) {
		    throw new NoRecordAvailableException("No Flight Records Available");
	    }

	    ResponseStructure<Page<Flight>> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Flights found");
        rs.setData(flights);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    
	}

}
