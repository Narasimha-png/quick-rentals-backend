package com.rentals.quickrentals.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rentals.quickrentals.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	Optional<Customer> findByDrivingLicenceNo(String drivingLicenceNo ) ;
}
