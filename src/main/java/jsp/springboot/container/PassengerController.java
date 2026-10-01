package jsp.springboot.container;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Passenger;
import jsp.springboot.enums.Gender;
import jsp.springboot.service.PassengerService;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

	@Autowired
	private PassengerService passengerService;
	
	@GetMapping("/{passengerId}")
	public ResponseEntity<ResponseStructure<Passenger>> getById(@PathVariable Integer passengerId){
		return passengerService.getById(passengerId);
	}
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAll(){
		return passengerService.getAll();
	}
	@GetMapping("/contact/{contactNumber}")
	public ResponseEntity<ResponseStructure<Passenger>> getByContactNumber(@PathVariable long contactNumber){
		return passengerService.getByContactNumber(contactNumber);
	}
	@GetMapping("/gender/{gender}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getByGender(@PathVariable Gender gender){
		return passengerService.getByGender(gender);
	}
	@GetMapping("/flight/{flightId}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getByFlight(@PathVariable Integer flightId){
		return passengerService.getByFlight(flightId);
	}
	@DeleteMapping("/booking/{bookingId}/passenger/{passengerId}")
	public ResponseEntity<ResponseStructure<Passenger>> deletePassengerFromBooking(@PathVariable Integer bookingId,@PathVariable Integer passengerId){
		return passengerService.deletePassengerFromBooking(bookingId,passengerId);
	}
	@PutMapping("/{passengerId}")
	public ResponseEntity<ResponseStructure<Passenger>> updatePassengerInfo(@PathVariable Integer passengerId,@RequestBody Passenger passenger){
		return passengerService.updatePassengerInfo(passengerId,passenger);
	}
}
