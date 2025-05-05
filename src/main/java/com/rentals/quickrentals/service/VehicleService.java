package com.rentals.quickrentals.service;

import java.util.List;

import com.rentals.quickrentals.dto.VehicleDTO;
import com.rentals.quickrentals.dto.VehicleTypeAndStatus;
import com.rentals.quickrentals.exception.VehicleException;

public interface VehicleService {
	
	public VehicleDTO getVehicleDetails(String vehicleNo) throws VehicleException ;
	public String addVehicle(VehicleDTO vehicleDTO) throws VehicleException ;
	public String deleteVehicle(VehicleDTO vehicleDTO) throws VehicleException ;
	public String updatebookingStatus(String vehicleNo, VehicleTypeAndStatus bookStatus) throws VehicleException;
	public List<VehicleDTO> getAllVehicles() throws VehicleException ;
	public List<VehicleDTO> getAllAvailableVehicles() throws VehicleException ;
}
