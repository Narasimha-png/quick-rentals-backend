package com.rentals.quickrentals.dto;

import lombok.Data;

@Data
public class VehicleDTO {
	private String vehicleNo ;
	private String vehicleName ;
	private Integer price ;

	private VehicleTypeAndStatus bookingStatus ;

	private VehicleTypeAndStatus vehicleType ; 
	
	
}
