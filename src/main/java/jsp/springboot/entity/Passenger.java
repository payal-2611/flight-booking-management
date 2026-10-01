	package jsp.springboot.entity;
	
	import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.EnumType;
	import jakarta.persistence.Enumerated;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	import jsp.springboot.enums.Gender;
	import lombok.AllArgsConstructor;
	import lombok.Getter;
	import lombok.NoArgsConstructor;
	import lombok.Setter;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	public class Passenger {
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer passengerId;
		
		@Column(nullable=false)
		private String name;
		
		@Column(nullable=false)
		private int age;
		
		@Column(nullable=false)
		@Enumerated(EnumType.STRING)
		private Gender gender;
		 
		@Column(nullable=false)
		private int seatNumber;
		
		@Column(unique=true,nullable=false)
		private long contactNumber;
		
		@ManyToOne
		@JoinColumn(name="booking_id",nullable=false)
		@JsonIgnore
		private Booking booking;
	}
