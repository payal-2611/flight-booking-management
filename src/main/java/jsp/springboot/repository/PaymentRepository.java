package jsp.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jsp.springboot.entity.Payment;
import jsp.springboot.enums.ModeOfPayment;
import jsp.springboot.enums.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

	List<Payment> findByStatus(PaymentStatus status);

	List<Payment> findByModeOfPayment(ModeOfPayment modeOfPayment);

	@Query("select sum(p.paymentAmount) from Payment p where p.status=:status")
	Double getTotalAmountPaid(@Param("status") PaymentStatus status);
}
