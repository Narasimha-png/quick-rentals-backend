package com.rentals.quickrentals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication

public class QuickrentalsApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LogManager.getLogger(QuickrentalsApplication.class) ;
	public static void main(String[] args) {
		SpringApplication.run(QuickrentalsApplication.class, args);
		
	}
	@Override
	public void run(String... args) throws Exception {
		
		
		
	}
	

}
