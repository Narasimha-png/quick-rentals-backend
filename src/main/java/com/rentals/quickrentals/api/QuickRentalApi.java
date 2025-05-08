package com.rentals.quickrentals.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.quickrentals.dto.CustomerDTO;
import com.rentals.quickrentals.dto.RentalHistoryDTO;
import com.rentals.quickrentals.dto.VehicleDTO;
import com.rentals.quickrentals.exception.CustomerException;
import com.rentals.quickrentals.exception.QuickRentalException;
import com.rentals.quickrentals.exception.VehicleException;
import com.rentals.quickrentals.service.CustomerServiceImpl;
import com.rentals.quickrentals.service.QuickVehicleServiceImpl;
import com.rentals.quickrentals.service.VehicleServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class QuickRentalApi {
	
	@Autowired 
	private VehicleServiceImpl vehicleService ;
	@Autowired
	private CustomerServiceImpl customerService ;
	@Autowired
	private QuickVehicleServiceImpl rentalService ;

	@GetMapping("availablebikes")
	public ResponseEntity<List<VehicleDTO>> getAvailableBike() throws VehicleException {
		return new ResponseEntity<>(vehicleService.getAllAvailableVehicles() , HttpStatus.OK) ;
	}
	
	@PostMapping("adduser")
	public ResponseEntity<String> addUser(@RequestBody CustomerDTO customerDTO) throws CustomerException{
		Integer id = customerService.addCustomer(customerDTO) ;
		String successMessage = "User Successfully created with id: " + id ;
		return new ResponseEntity<String>(successMessage , HttpStatus.CREATED ) ;
	}
	
	@PostMapping("bookvehicle")
	public ResponseEntity<String> bookVehicle(Integer customerId , RentalHistoryDTO rentalHistory ) throws QuickRentalException{
		String vehicleNo = rentalService.bookMyBike(customerId, rentalHistory) ;
		String successMessage = "Booking has done successfully.\n Bike Number " + vehicleNo ;
		return new ResponseEntity<String>(successMessage , HttpStatus.OK) ;
		
	}
	
	@GetMapping("getuserrentalhistory")
	public ResponseEntity<List<RentalHistoryDTO>> getRentalHistoryByDrivingLicence(CustomerDTO customerDTO , Integer page , Integer size ) throws QuickRentalException{
		return new ResponseEntity<List<RentalHistoryDTO>>(rentalService.viewRentalHistory(customerDTO.getDrivingLicenceNo() , page , size ) , HttpStatus.OK) ; 
	}
	
	@GetMapping("getvehiclerentalhistory")
	public ResponseEntity<List<RentalHistoryDTO>> getRentalHistoryByVehicleNo(String vehicleNo) throws QuickRentalException{
		return new ResponseEntity<List<RentalHistoryDTO>>(rentalService.viewRentalHistory(vehicleNo) , HttpStatus.OK) ;
	}
	
	
	
}
