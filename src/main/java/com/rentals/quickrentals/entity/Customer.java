package com.rentals.quickrentals.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer customerId ;
	private String customerName ;
	private String drivingLicenceNo ;
	private Integer age ;
	
	@OneToMany
	@JoinColumn(name="customer_id")
	private List<RentalHistory> rentalHistories ;
}
