package com.rentals.quickrentals.service;

import com.rentals.quickrentals.dto.CustomerDTO;
import com.rentals.quickrentals.exception.CustomerException;

public interface CustomerService {
	public CustomerDTO getCustomerDetails(String drivingLicenceNumber) throws CustomerException ;
	public Integer addCustomer(CustomerDTO customerDTO) throws CustomerException ;
	public Integer delteCustomer(CustomerDTO customerDTO) throws CustomerException ;
} 
