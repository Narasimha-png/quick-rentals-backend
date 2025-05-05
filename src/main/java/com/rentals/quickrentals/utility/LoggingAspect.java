package com.rentals.quickrentals.utility;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rentals.quickrentals.exception.CustomerException;
import com.rentals.quickrentals.exception.QuickRentalException;
import com.rentals.quickrentals.exception.VehicleException;

@Aspect
@Component
public class LoggingAspect {

	@Autowired
	Environment enviromment ;

	private final Logger LOGGER = LogManager.getLogger(LoggingAspect.class)  ;
	
	@AfterThrowing(pointcut = "execution(* com.rentals.quickrentals.service.QuickVehicleServiceImpl.*(..))", throwing="exception")
	public void getLogging(QuickRentalException exception ) {
		String message = enviromment.getProperty(exception.getMessage()) ;
		LOGGER.error(message) ;
	}
	@AfterThrowing(pointcut = "execution(* com.rentals.quickrentals.service.CustomerServiceImpl.*(..))", throwing="exception")
	public void getLogging(CustomerException exception ) {
		String message = enviromment.getProperty(exception.getMessage()) ;
		LOGGER.error(message) ;
	}
	
	@AfterThrowing(pointcut = "execution(* com.rentals.quickrentals.service.VehicleServiceImpl.*(..))", throwing="exception")
	public void getLogging(VehicleException exception ) {
		String message = enviromment.getProperty(exception.getMessage()) ;
		LOGGER.error(message) ;
	}
}
