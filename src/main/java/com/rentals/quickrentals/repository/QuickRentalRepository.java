package com.rentals.quickrentals.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rentals.quickrentals.entity.RentalHistory;

public interface QuickRentalRepository extends CrudRepository<RentalHistory, Integer> {
	List<RentalHistory> findByVehicle_VehicleNo(String VehicleNo) ;
	List<RentalHistory> findByPickUpDateTimeAfter(LocalDateTime startTime ) ;
	List<RentalHistory> findByDropOffDateTimeAfter(LocalDateTime startTime ) ;
	List<RentalHistory> findByPickUpDateTimeBetween(LocalDateTime startTime , LocalDateTime endTime) ;
	List<RentalHistory> findByDropOffDateTimeBetween(LocalDateTime startTime , LocalDateTime endTime) ;
	Page<RentalHistory> findByCustomerDrivingLicenceNo(String drivingLicenceNo , Pageable pageable ) ;
}
