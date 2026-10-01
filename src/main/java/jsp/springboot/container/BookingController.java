package jsp.springboot.container;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jsp.springboot.dto.ResponseStructure;
import org.springframework.http.ResponseEntity;

import jsp.springboot.entity.Booking;
import jsp.springboot.entity.Passenger;
import jsp.springboot.entity.Payment;
import jsp.springboot.enums.Status;
import jsp.springboot.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> saveBooking(@RequestBody Booking booking){
		return bookingService.saveBooking(booking);
	}
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBooking(){
		return bookingService.getAllBooking();
	}
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Booking>> getById(@PathVariable Integer id){
		return bookingService.getById(id);
	}
	@GetMapping("/flight/{flightId}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getByFlight(@PathVariable Integer flightId){
		return bookingService.getByFlight(flightId);
	}
	@GetMapping("/date")
	public ResponseEntity<ResponseStructure<List<Booking>>> getByDate(@RequestParam LocalDateTime start,@RequestParam LocalDateTime end){
		return bookingService.getByDate(start,end);
	}
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getByFlight(@PathVariable Status status){
		return bookingService.getByStatus(status);
	}
	@GetMapping("/passenger/{bookingId}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengersInBooking(@PathVariable Integer bookingId){
		return bookingService.getAllPassengersInBooking(bookingId);
	}
	@GetMapping("/payment/{bookingId}")
	public ResponseEntity<ResponseStructure<Payment>> getAllPaymentsInBooking(@PathVariable Integer bookingId){
		return bookingService.getAllPaymentsInBooking(bookingId);
	}
	@PutMapping("/status/{bookingId}")
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(@PathVariable Integer bookingId,  @RequestParam Status status){
		return bookingService.updateBookingStatus(bookingId,status);
	}
	@DeleteMapping("/delete/{bookingId}")
	public ResponseEntity<ResponseStructure<String>> deleteBooking(@PathVariable Integer bookingId){
		return bookingService.deleteBooking(bookingId);
	}
	@GetMapping("/booking-history/{PassengerId}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingHistory(@PathVariable Integer PassengerId) {
	    return bookingService.getBookingHistory(PassengerId);
	}
}
