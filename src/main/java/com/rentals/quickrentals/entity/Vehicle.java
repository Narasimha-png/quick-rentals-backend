package com.rentals.quickrentals.entity;

import com.rentals.quickrentals.dto.VehicleTypeAndStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Vehicle {
	@Id
	private String vehicleNo ;
	private String vehicleName ;
	private Integer price ;
	
	@Enumerated(EnumType.STRING)
	private VehicleTypeAndStatus bookingStatus ;
	@Enumerated(EnumType.STRING)
	private VehicleTypeAndStatus vehicleType ;
	
}
