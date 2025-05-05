package com.rentals.quickrentals.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rentals.quickrentals.dto.VehicleTypeAndStatus;
import com.rentals.quickrentals.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {
	public Optional<Vehicle> findByVehicleNo(String vehicleNo) ;
	public List<Vehicle> findByBookingStatusEquals(VehicleTypeAndStatus bookingStatus) ;
}
