package com.rentals.quickrentals.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rentals.quickrentals.dto.CustomerDTO;
import com.rentals.quickrentals.dto.RentalHistoryDTO;
import com.rentals.quickrentals.dto.VehicleDTO;
import com.rentals.quickrentals.exception.QuickRentalException;

public interface QuickVehicleService {
	public List<RentalHistoryDTO> viewRentalHistory(String vehicleNo) throws QuickRentalException ;
	public List<RentalHistoryDTO> viewRentalHistory(String drivingLicence , int page , int size ) throws QuickRentalException ;
	public List<RentalHistoryDTO> viewPickUpDateAndTimeAfter(LocalDateTime startTime ) throws QuickRentalException;
	public List<RentalHistoryDTO> viewDropOffDateAndTimeAfter(LocalDateTime startTime ) throws QuickRentalException;
	public List<RentalHistoryDTO> viewPickUpDateAndTimeBetween(LocalDateTime startTime , LocalDateTime endTime) throws QuickRentalException;
	public List<RentalHistoryDTO> viewDropOffDateAndTimeBetween(LocalDateTime startTime , LocalDateTime endTime) throws QuickRentalException;
	public String bookMyBike(Integer customerId , RentalHistoryDTO rental ) throws QuickRentalException ;
	
}
