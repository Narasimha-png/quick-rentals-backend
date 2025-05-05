package com.rentals.quickrentals.entity;


import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class RentalHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	private LocalDateTime pickUpDateTime ;
	private LocalDateTime dropOffDateTime ;
	
	@ManyToOne(cascade = CascadeType.ALL )
	@JoinColumn(name="vehicle_no")
	private Vehicle vehicle ;
	
	@ManyToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "customer_id")
	private Customer customer;


}
