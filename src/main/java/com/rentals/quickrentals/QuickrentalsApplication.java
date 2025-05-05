package com.rentals.quickrentals;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.rentals.quickrentals.dto.CustomerDTO;
import com.rentals.quickrentals.dto.RentalHistoryDTO;
import com.rentals.quickrentals.dto.VehicleDTO;
import com.rentals.quickrentals.dto.VehicleTypeAndStatus;
import com.rentals.quickrentals.entity.RentalHistory;
import com.rentals.quickrentals.service.CustomerServiceImpl;
import com.rentals.quickrentals.service.QuickVehicleServiceImpl;
import com.rentals.quickrentals.service.VehicleServiceImpl;

@SpringBootApplication

public class QuickrentalsApplication implements CommandLineRunner {

	@Autowired
	Environment environment ;
	
	@Autowired
	private CustomerServiceImpl cust ;
	@Autowired
	private VehicleServiceImpl vehicleService ;
	@Autowired
	private QuickVehicleServiceImpl rentalService ;
	
	private static final Logger LOGGER = LogManager.getLogger(QuickrentalsApplication.class) ;
	public static void main(String[] args) {
		SpringApplication.run(QuickrentalsApplication.class, args);
		
	}
	@Override
	public void run(String... args) throws Exception {
		try {
		List<VehicleDTO> dtos = vehicleService.getAllAvailableVehicles() ;
		LOGGER.info(dtos) ;
		}
		catch(Exception e) {}
		
		
	}
	

}
