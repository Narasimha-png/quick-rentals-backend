package com.rentals.quickrentals.utility;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rentals.quickrentals.exception.CustomerException;
import com.rentals.quickrentals.exception.QuickRentalException;
import com.rentals.quickrentals.exception.VehicleException;

@RestControllerAdvice
@Component
public class ExceptionControllerAdvice {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionControllerAdvice.class) ;
	
	@Autowired
	Environment environment ;
	
	@ExceptionHandler(VehicleException.class) 
	public ResponseEntity<ErrorInfo> vehicleExceptionHandler(VehicleException exception){
	
		LOGGER.error(exception.getMessage() , exception ) ;
		
		ErrorInfo errorInfo = new ErrorInfo() ;
		errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
		String errorMessage = environment.getProperty(exception.getMessage()) ;
		errorInfo.setErrorMessage(errorMessage!=null ? errorMessage : "Vehicle not found");
		errorInfo.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<ErrorInfo>(errorInfo , HttpStatus.NOT_FOUND) ;
	}
	@ExceptionHandler(CustomerException.class) 
	public ResponseEntity<ErrorInfo> CustomerExceptionHandler(CustomerException exception){
	
		LOGGER.error(exception.getMessage() , exception ) ;
		
		ErrorInfo errorInfo = new ErrorInfo() ;
		
		errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorInfo.setErrorMessage(environment.getProperty(exception.getMessage()));
		errorInfo.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<ErrorInfo>(errorInfo , HttpStatus.NOT_FOUND) ;
	}
	
	@ExceptionHandler(QuickRentalException.class) 
	public ResponseEntity<ErrorInfo> QuickRetalExceptionHandler(QuickRentalException exception){
	
		LOGGER.error(exception.getMessage() , exception ) ;
		
		ErrorInfo errorInfo = new ErrorInfo() ;
		
		errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorInfo.setErrorMessage(environment.getProperty(exception.getMessage()));
		errorInfo.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<ErrorInfo>(errorInfo , HttpStatus.NOT_FOUND) ;
	}
}
