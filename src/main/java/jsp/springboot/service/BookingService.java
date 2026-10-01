package jsp.springboot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Booking;
import jsp.springboot.entity.Flight;
import jsp.springboot.entity.Passenger;
import jsp.springboot.entity.Payment;
import jsp.springboot.enums.PaymentStatus;
import jsp.springboot.enums.Status;
import jsp.springboot.exception.NoRecordAvailableException;
import jsp.springboot.repository.BookingRepository;
import jsp.springboot.repository.FlightRepository;
import jsp.springboot.repository.PassengerRepository;
import jsp.springboot.repository.PaymentRepository;

@Service
public class BookingService {

	@Autowired 
	private BookingRepository bookingRepository;
	
	@Autowired 
	private PassengerRepository passengerRepository;
	
	@Autowired 
	private PaymentRepository paymentRepository;
	
	@Autowired 
	private FlightRepository flightRepository;
	
	
	//Create Booking
	@Transactional
	public ResponseEntity<ResponseStructure<Booking>> saveBooking(Booking booking) {
		
		//Check Flight available or not
		Optional<Flight> opt1=flightRepository.findById(booking.getFlight().getFlightId());
		
		if(opt1.isPresent()) { 
		    booking.setFlight(opt1.get());
		}	
		else 
		    throw new NoRecordAvailableException("Flight not found");	
			
		//check passengers details
		List<Passenger> passengers= booking.getPassengers();
		
		if(passengers == null ||passengers.isEmpty())
		    throw new NoRecordAvailableException("Passenger details are required");	
		
		//save passengers details
		for(Passenger passenger:passengers) {
			passenger.setBooking(booking);
		}
		
		//Check for Available of Seats
		int requiredSeats=passengers.size();
		int availableSeats=booking.getFlight().getAvailableSeats();
		
		if(availableSeats>=requiredSeats){
			booking.getFlight().setAvailableSeats(availableSeats-requiredSeats);
			
			// Update Flight table
		    flightRepository.save(booking.getFlight());
		}	
		else
			throw new NoRecordAvailableException("Not enough seats available");
		
		//check payment details
		Payment payment=booking.getPayment();
		
		if(payment==null)
			throw new NoRecordAvailableException("Payment details are required");
			
		
		// Calculate payment based on number of passengers
		double ticketPrice=booking.getFlight().getPrice();
		double totalAmount=ticketPrice*requiredSeats;
		payment.setPaymentAmount(totalAmount);
		payment.setBooking(booking);
		
		// Set payment status
		payment.setStatus(PaymentStatus.PAID);
		
		// Booking status
        booking.setStatus(Status.CONFIRMED);
        
		//save booking
		Booking savedBooking=bookingRepository.save(booking);
		
		//save all passengers
		passengerRepository.saveAll(passengers);
		
		//save payment
		paymentRepository.save(payment);
		
		ResponseStructure<Booking> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Booking saved successfully!");
		res.setData(savedBooking);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
		
	}

	//GET ALL BOOKING
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBooking() {
		List<Booking> bookings =bookingRepository.findAll();
		
		ResponseStructure<List<Booking>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Get All Booking successfully!");
		res.setData(bookings);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	//GET BY ID
	public ResponseEntity<ResponseStructure<Booking>> getById(Integer id) {
		Optional<Booking> booking=bookingRepository.findById(id);
		
		ResponseStructure<Booking> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Booking Fetched successfully!");
		res.setData(booking.get());
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	//GET BOOKING BY FLIGHT 
	public ResponseEntity<ResponseStructure<List<Booking>>> getByFlight(Integer flightId) {
		List<Booking> bookings =bookingRepository.findByFlight_FlightId(flightId);
		
		ResponseStructure<List<Booking>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Get All Booking successfully!");
		res.setData(bookings);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	//GET BOOKING BY DATE
	public ResponseEntity<ResponseStructure<List<Booking>>> getByDate(LocalDateTime start,LocalDateTime end) {
		List<Booking> bookings =bookingRepository.findByBookingDateTimeBetween(start,end);
		
		ResponseStructure<List<Booking>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Get All Booking successfully!");
		res.setData(bookings);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	//GET BOOKING BY STATUS
	public ResponseEntity<ResponseStructure<List<Booking>>> getByStatus(Status status) {
		List<Booking> bookings =bookingRepository.findByStatus(status);
		
		ResponseStructure<List<Booking>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Get All Booking successfully!");
		res.setData(bookings);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	//GET ALL PASSENGERS IN A BOOKING
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengersInBooking(Integer bookingId) {
		Optional<Booking> opt =bookingRepository.findById(bookingId);
		
		if(opt.isEmpty()) {
			throw new NoRecordAvailableException("Booking not found for id: "+bookingId);			
		}
		
		Booking booking=opt.get();
		List<Passenger> passengers = booking.getPassengers();
		
		ResponseStructure<List<Passenger>> res=new ResponseStructure<>();
		
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("All Passengers fetched successfully! for booKing ID : "+bookingId);
		res.setData(passengers);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	//GET ALL PAYMENT IN A BOOKING
	public ResponseEntity<ResponseStructure<Payment>> getAllPaymentsInBooking(Integer bookingId) {
		Optional<Booking> opt =bookingRepository.findById(bookingId);
		
		if(opt.isEmpty()) {
			throw new NoRecordAvailableException("Booking not found for id: "+bookingId);			
		}
		
		Booking booking=opt.get();
		Payment payments = booking.getPayment();
		
		ResponseStructure<Payment> res=new ResponseStructure<>();
		
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("All payments fetched successfully for booKing ID : "+bookingId);
		res.setData(payments);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	//Update Booking Status
	@Transactional
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(Integer bookingId, Status status) {
		Optional<Booking> opt =bookingRepository.findById(bookingId);
		
		if(opt.isEmpty()) {
			throw new NoRecordAvailableException("Booking not found for id: "+bookingId);			
		}
		
		Booking booking=opt.get();
		
		//update booking status
		booking.setStatus(status);
		
		if(status==Status.CANCELLED) {
			
			//get no of passengers
			int passengerCount = booking.getPassengers().size();
			
			//add seat back to flight
			Flight flight = booking.getFlight();
			
			flight.setAvailableSeats(flight.getAvailableSeats()+passengerCount);
			flightRepository.save(flight);
			
			//update payment status
			Payment payment =booking.getPayment();
			
			if(payment!=null) {
				payment.setStatus(PaymentStatus.REFUND);
				paymentRepository.save(payment);
			}
		}
	    if (status == Status.PENDING) {
	        Payment payment = booking.getPayment();

	        if (payment != null) {
	            payment.setStatus(PaymentStatus.FAILED);
	            paymentRepository.save(payment);
	        }
	    }
	    
	    if (status == Status.CONFIRMED) {
	        Payment payment = booking.getPayment();

	        if (payment == null) {
	            throw new NoRecordAvailableException("Payment not found");
	        }
            payment.setStatus(PaymentStatus.PAID);
	        booking.setStatus(Status.CONFIRMED);
	    }
		
	    Booking updatedBooking = bookingRepository.save(booking);

		ResponseStructure<Booking> res=new ResponseStructure<>();
		
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Booking Status Updated successfully!");
		res.setData(updatedBooking);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	//delete booking
	public ResponseEntity<ResponseStructure<String>> deleteBooking(Integer bookingId) {
		
		Optional<Booking> opt =bookingRepository.findById(bookingId);
		
		if(opt.isEmpty()) {
			 ResponseStructure<String> res = new ResponseStructure<>();

			    res.setStatusCode(HttpStatus.NOT_FOUND.value());
			    res.setMessage("Booking not found!");
			    res.setData("No booking exists with id: " + bookingId);

			    return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}
		
		Booking booking=opt.get();
		
		Flight flight=booking.getFlight();
	    List<Passenger> passengers = booking.getPassengers();

	    //release seats
	    if(passengers!=null && !passengers.isEmpty()) {
	    	 
	    	int passengerCount= passengers.size();
	    	flight.setAvailableSeats(flight.getAvailableSeats()+passengerCount);
	        flightRepository.save(flight);
	        
	        passengerRepository.deleteAll(passengers);
	    }
	    //delete payment
	    Payment payment=booking.getPayment();
	    
	    if(payment!=null) {
	    	paymentRepository.delete(payment);
	    }
	    
	    //delete booking
	    bookingRepository.delete(booking);
	    
	    ResponseStructure<String> res=new ResponseStructure<>();
	    res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Booking deleted successfully!");
	    res.setData("Booking ID " + bookingId + " deleted successfully");

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}

	//GET BOOKING HISTROY OF PASSENGERS
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingHistory(Integer passengerId) {

	    List<Booking> bookings =bookingRepository.findByPassengersPassengerId(passengerId);

	    ResponseStructure<List<Booking>> rs = new ResponseStructure<>();

	    if (bookings.isEmpty()) {
            throw new NoRecordAvailableException("No Record Found");
	    }

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Booking history found");
        rs.setData(bookings);

        return new ResponseEntity<>(rs, HttpStatus.OK);
	}
}
