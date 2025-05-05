package com.rentals.quickrentals.dto;

import java.util.List;

import com.rentals.quickrentals.entity.RentalHistory;

import lombok.Data;

@Data
public class CustomerDTO {
	private Integer customerId ;
	private String customerName ;
	private String drivingLicenceNo ;
	private Integer age ;
	
	private List<RentalHistoryDTO> rentalHistories ;
}
