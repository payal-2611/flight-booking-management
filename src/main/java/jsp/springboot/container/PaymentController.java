package jsp.springboot.container;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Payment;
import jsp.springboot.enums.ModeOfPayment;
import jsp.springboot.enums.PaymentStatus;
import jsp.springboot.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Payment>>> getAll(){
		return paymentService.getAll();
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<ResponseStructure<Payment>> getById(@PathVariable Integer paymentId){
		return paymentService.getById(paymentId);
	}
	//Update flight
	@PutMapping("/{paymentId}/status/{status}")
	public ResponseEntity<ResponseStructure<Payment>> updateStatus(@PathVariable Integer paymentId, @PathVariable PaymentStatus status){
		return paymentService.updateStatus(paymentId,status);
	}
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getByStatus(@PathVariable PaymentStatus status){
		return paymentService.getByStatus(status);
	}
	@GetMapping("/modeofpayment/{modeOfPayment}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getByModeOfPayment(@PathVariable ModeOfPayment modeOfPayment){
		return paymentService.getByModeOfPayment(modeOfPayment);
	}
	@GetMapping("/total-amount")
	public ResponseEntity<ResponseStructure<Double>> getTotalAmount(){
		return paymentService.getTotalAmount();
	}
}
