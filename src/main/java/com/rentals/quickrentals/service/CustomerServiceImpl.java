package com.rentals.quickrentals.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentals.quickrentals.dto.CustomerDTO;
import com.rentals.quickrentals.dto.RentalHistoryDTO;
import com.rentals.quickrentals.dto.VehicleDTO;
import com.rentals.quickrentals.entity.Customer;
import com.rentals.quickrentals.entity.RentalHistory;
import com.rentals.quickrentals.exception.CustomerException;
import com.rentals.quickrentals.exception.QuickRentalException;
import com.rentals.quickrentals.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository ;
	
	private final ModelMapper modelMapper = new ModelMapper() ;

	@Override
	public CustomerDTO getCustomerDetails(String drivingLicenceNo) throws CustomerException {
		
		Optional<Customer> optional = customerRepository.findByDrivingLicenceNo(drivingLicenceNo) ;
		
		Customer customer = optional.orElseThrow(()-> new CustomerException("CustomerException.CUSTOMER_NOT_FOUND")) ;
		
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class) ;
		
		List<RentalHistoryDTO> rentalHistories = customer.getRentalHistories().stream().map((rentalHistory)->{
			RentalHistoryDTO rentalHistoryDTO = modelMapper.map(rentalHistory, RentalHistoryDTO.class) ;
			VehicleDTO vehicleDTO = modelMapper.map(rentalHistory.getVehicle() , VehicleDTO.class) ;
			rentalHistoryDTO.setVehicle(vehicleDTO) ;
			return rentalHistoryDTO ;
			}).sorted((rentalHistoryObj1 , rentalHistoryObj2)-> 
			rentalHistoryObj1.getPickUpDateTime().isBefore(rentalHistoryObj2.getPickUpDateTime()) ? -1 :1 ) 
			.toList() ;
		
		customerDTO.setRentalHistories(rentalHistories);
		
		return customerDTO ;
	}

	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws CustomerException {
		Optional<Customer> optional = customerRepository.findByDrivingLicenceNo(customerDTO.getDrivingLicenceNo()) ;
		
		if(optional.isPresent())
		throw new CustomerException("CustomerException.CUSTOMER_ALREADY_EXISTS") ;
		
		Customer customer = modelMapper.map(customerDTO, Customer.class) ;
		customer.setRentalHistories(new ArrayList<RentalHistory>());
		customerRepository.save(customer) ;
		return customer.getCustomerId() ;
	}

	
	/*
	 * It deletes only customer, 
	 * when he signup again ,
	 * then old rental history will be there.
	 */

	@Override
	public Integer delteCustomer(CustomerDTO customerDTO) throws CustomerException {
		Optional<Customer> optional = customerRepository.findByDrivingLicenceNo(customerDTO.getDrivingLicenceNo()) ;
		
		Customer customer = optional.orElseThrow(()-> new CustomerException("CustomerException.CUSTOMER_NOT_FOUND")) ;
		
		customerRepository.delete(customer);
		
		return customer.getCustomerId() ;
	}

	
	
}
