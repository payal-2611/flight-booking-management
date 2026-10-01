package jsp.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Payment;
import jsp.springboot.enums.ModeOfPayment;
import jsp.springboot.enums.PaymentStatus;
import jsp.springboot.exception.NoRecordAvailableException;
import jsp.springboot.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;

	public ResponseEntity<ResponseStructure<List<Payment>>> getAll() {
		List<Payment> payments=paymentRepository.findAll();
		
		if(payments.isEmpty())
	        throw new NoRecordAvailableException("No Records Available");
		
		ResponseStructure<List<Payment>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("All payment record fetched successfully");
		res.setData(payments);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Payment>> getById(Integer paymentId) {
		Optional<Payment> payment=paymentRepository.findById(paymentId);
		
		if(payment.isEmpty())
	        throw new NoRecordAvailableException("No Records Available for id : "+paymentId);
		
		ResponseStructure<Payment> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("payment record fetched successfully for id: "+paymentId);
		res.setData(payment.get());
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}

	//Update Payment status
	public ResponseEntity<ResponseStructure<Payment>> updateStatus(Integer paymentId, PaymentStatus status) {
		Optional<Payment> opt =paymentRepository.findById(paymentId);
		
		if(opt.isEmpty())
	        throw new NoRecordAvailableException("No Records Available for id : "+paymentId);
		
		Payment payment =opt.get();
		
		payment.setStatus(status);
		
		Payment updatedPayment =paymentRepository.save(payment);
		
		ResponseStructure<Payment> rs = new ResponseStructure<>();
	        rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Payment status updated successfully");
	        rs.setData(updatedPayment);

	        return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	//GET BY STATUS
	public ResponseEntity<ResponseStructure<List<Payment>>> getByStatus(PaymentStatus status) {
		List<Payment> payments =paymentRepository.findByStatus(status);
		
		if(payments.isEmpty())
	        throw new NoRecordAvailableException("No Records Found");
								
		ResponseStructure<List<Payment>> rs = new ResponseStructure<>();
	        rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Fetch All Payment Record successfully");
	        rs.setData(payments);

	        return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	//GET BY MODE OF PAYMENT
	public ResponseEntity<ResponseStructure<List<Payment>>> getByModeOfPayment(ModeOfPayment modeOfPayment) {
		List<Payment> payments =paymentRepository.findByModeOfPayment(modeOfPayment);
		
		if(payments.isEmpty())
	        throw new NoRecordAvailableException("No Records Found");
								
		ResponseStructure<List<Payment>> rs = new ResponseStructure<>();
		  	rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Fetch All Payment Record successfully");
	        rs.setData(payments);

	        return new ResponseEntity<>(rs, HttpStatus.OK);  
	}

	//GET TOTAL AMOUNT PAID ON FLIGHT BASED ON ALL BOOKING
	public ResponseEntity<ResponseStructure<Double>> getTotalAmount() {
		
		 Double total=paymentRepository.getTotalAmountPaid(PaymentStatus.PAID);
		 
		 if(total==null)
			 total = 0.0;
		 
		 ResponseStructure<Double> rs = new ResponseStructure<>();
		    rs.setStatusCode(HttpStatus.OK.value());
		    rs.setMessage("Total amount paid for all bookings");
		    rs.setData(total);

		    return new ResponseEntity<>(rs, HttpStatus.OK);
	}
}

	