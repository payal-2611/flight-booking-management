package jsp.springboot.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jsp.springboot.enums.PaymentStatus;
import jsp.springboot.enums.ModeOfPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer paymentId;
	
	@Column(nullable=false)
	@CreationTimestamp
	private LocalDateTime paymentDateTime;
	
	@Column(nullable=false)
	private Double paymentAmount;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private ModeOfPayment modeOfPayment;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	@OneToOne
	@JoinColumn(name="booking_id",nullable=true)
	@JsonIgnore
	private Booking booking;
}
