package com.rentals.quickrentals.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentals.quickrentals.dto.VehicleDTO;
import com.rentals.quickrentals.dto.VehicleTypeAndStatus;
import com.rentals.quickrentals.entity.Vehicle;
import com.rentals.quickrentals.exception.VehicleException;
import com.rentals.quickrentals.repository.VehicleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService{

	
	@Autowired
	private VehicleRepository vehicleRepository ;
	private final ModelMapper modelMapper = new ModelMapper() ;
	
	private Logger LOGGER = LogManager.getLogger(VehicleServiceImpl.class) ;
	
	@Override
	public VehicleDTO getVehicleDetails(String vehicleNo) throws VehicleException {
		Optional<Vehicle> optional = vehicleRepository.findById(vehicleNo) ;
		
		Vehicle vehicle = optional.orElseThrow(()->new VehicleException("VehicleException.VEHICLE_NOT_FOUND")) ;
		
		return modelMapper.map(vehicle, VehicleDTO.class) ;
	}

	@Override
	public String addVehicle(VehicleDTO vehicleDTO) throws VehicleException {
		Optional<Vehicle> optional = vehicleRepository.findById(vehicleDTO.getVehicleNo()) ;
		
		if(optional.isPresent())
		throw new VehicleException("VehicleException.VEHICLE_ALREADY_PRESENT") ;
		
		Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class) ;
		vehicleRepository.save(vehicle) ;
		return vehicle.getVehicleNo() ;
	}


	@Override
	public String deleteVehicle(VehicleDTO vehicleDTO) throws VehicleException {
		Optional<Vehicle> optional = vehicleRepository.findById(vehicleDTO.getVehicleNo()) ;
		
		Vehicle vehicle = optional.orElseThrow(()->new VehicleException("VehicleException.VEHICLE_NOT_FOUND")) ;
		
		vehicleRepository.delete(vehicle);
		
		return vehicle.getVehicleNo() ;
		
		
	}

	@Override
	public String updatebookingStatus(String vehicleNo, VehicleTypeAndStatus bookStatus) throws VehicleException {
		Optional<Vehicle> optional = vehicleRepository.findById(vehicleNo) ;
		
		Vehicle vehicle = optional.orElseThrow(()->new VehicleException("VehicleException.VEHICLE_NOT_FOUND")) ;
		
		vehicle.setBookingStatus(bookStatus);
		
		return vehicleNo ;
	}

	@Override
	public List<VehicleDTO> getAllVehicles() throws VehicleException {
		List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findAll() ;
		List<VehicleDTO> vehicleDTOs = vehicles.stream().map(vehicle-> modelMapper.map(vehicle, VehicleDTO.class)).toList() ;
		return vehicleDTOs ;
	}

	@Override
	public List<VehicleDTO> getAllAvailableVehicles() throws VehicleException {
		List<Vehicle> vehicles = vehicleRepository.findByBookingStatusEquals(VehicleTypeAndStatus.AVAILABLE) ;
		
		LOGGER.error(vehicles) ;
		
		if (vehicles.isEmpty()) {
	        throw new VehicleException("VehicleException.NO_VEHICLES_LEFT");
	    }
		
		List<VehicleDTO> vehicleDTOs = vehicles.stream().map((vehicle)-> modelMapper.map(vehicle, VehicleDTO.class)).toList() ;
		
		return vehicleDTOs ;
		
	}

	
	
}
