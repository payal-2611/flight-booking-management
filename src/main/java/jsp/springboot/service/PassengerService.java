package jsp.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Passenger;
import jsp.springboot.enums.Gender;
import jsp.springboot.exception.NoRecordAvailableException;
import jsp.springboot.repository.PassengerRepository;

@Service
public class PassengerService {

	@Autowired
	private PassengerRepository passengerRepository;

	//GET PASSENGER BY ID
	public ResponseEntity<ResponseStructure<Passenger>> getById(Integer passengerId) {
		Optional<Passenger> opt= passengerRepository.findById(passengerId);
		
		if(opt.isEmpty())
			throw new NoRecordAvailableException("Passenger not available for id : "+passengerId);
		
		ResponseStructure<Passenger> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("passenger record fetched successfully!");
		res.setData(opt.get());
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);		
	}

	//GET ALL PASSENGERS 
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAll() {
		List<Passenger> opt= passengerRepository.findAll();
		
		if(opt.isEmpty())
			throw new NoRecordAvailableException("No Record Found");
		
		ResponseStructure<List<Passenger>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("All passenger record fetched successfully!");
		res.setData(opt);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	//GET PASSENGER BY CONTACT NUMBER
	public ResponseEntity<ResponseStructure<Passenger>> getByContactNumber(long contactNumber) {
		Optional<Passenger> opt= passengerRepository.findByContactNumber(contactNumber);
		
		if(opt.isEmpty())
			throw new NoRecordAvailableException("Passenger record not available");
		
		ResponseStructure<Passenger> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("passenger record fetched successfully!");
		res.setData(opt.get());
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	//GET PASSENGER BY GENDER
	public ResponseEntity<ResponseStructure<List<Passenger>>> getByGender(Gender gender) {
		List<Passenger> opt= passengerRepository.findByGender(gender);
		
		if(opt.isEmpty())
			throw new NoRecordAvailableException("No Record Found");
		
		ResponseStructure<List<Passenger>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("All passenger record fetched successfully!");
		res.setData(opt);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	//GET BY FLIGHT
	public ResponseEntity<ResponseStructure<List<Passenger>>> getByFlight(Integer flightId) {
		List<Passenger> opt= passengerRepository.findPassengersByFlight(flightId);
		
		if(opt.isEmpty())
			throw new NoRecordAvailableException("No Record Found");
		
		ResponseStructure<List<Passenger>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("All passenger record fetched successfully!");
		res.setData(opt);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	//DELETE PASSENGER FROM BOOKING
	public ResponseEntity<ResponseStructure<Passenger>> deletePassengerFromBooking(Integer bookingId, Integer passengerId) {
		Optional<Passenger> opt=passengerRepository.findById(passengerId);
	
		ResponseStructure<Passenger> rs=new ResponseStructure<>();
		Passenger passenger =opt.get();
		
		if(passenger.getBooking().getBookingId()== bookingId) {
			passengerRepository.delete(passenger);
			rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Passenger deleted from booking");
	        rs.setData(passenger);

	        return new ResponseEntity<>(rs, HttpStatus.OK);	  		
		}
		
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
        rs.setMessage("Passenger does not belong to this booking");
        rs.setData(null);
        
        return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
	}

	//UPDATE PASSENGER INFORMATION
	public ResponseEntity<ResponseStructure<Passenger>> updatePassengerInfo(Integer passengerId, Passenger passenger) {
		Optional<Passenger> opt=passengerRepository.findById(passengerId);

		if(opt.isEmpty())
			throw new NoRecordAvailableException("No Record Found");

		Passenger existingPassenger =opt.get();
		existingPassenger.setName(passenger.getName());
		existingPassenger.setAge(passenger.getAge());
		existingPassenger.setGender(passenger.getGender());
		existingPassenger.setSeatNumber(passenger.getSeatNumber());
		existingPassenger.setContactNumber(passenger.getContactNumber());
		
		Passenger updatedPassenger=passengerRepository.save(existingPassenger);
		
		ResponseStructure<Passenger> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
	    rs.setMessage("Passenger information updated successfully");
	    rs.setData(updatedPassenger);

	    return new ResponseEntity<>(rs, HttpStatus.OK);
	}

}
