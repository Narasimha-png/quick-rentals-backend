package com.rentals.quickrentals.dto;

import java.time.LocalDateTime;

import com.rentals.quickrentals.entity.Vehicle;

import lombok.Data;


@Data
public class RentalHistoryDTO {
	private Integer id ;
	private LocalDateTime pickUpDateTime ;
	private LocalDateTime dropOffDateTime ;
	
	private VehicleDTO vehicle ;
}
