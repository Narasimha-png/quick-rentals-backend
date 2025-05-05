package com.rentals.quickrentals.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rentals.quickrentals.dto.CustomerDTO;
import com.rentals.quickrentals.dto.RentalHistoryDTO;
import com.rentals.quickrentals.dto.VehicleDTO;
import com.rentals.quickrentals.dto.VehicleTypeAndStatus;
import com.rentals.quickrentals.entity.Customer;
import com.rentals.quickrentals.entity.RentalHistory;
import com.rentals.quickrentals.entity.Vehicle;
import com.rentals.quickrentals.exception.CustomerException;
import com.rentals.quickrentals.exception.QuickRentalException;
import com.rentals.quickrentals.exception.VehicleException;
import com.rentals.quickrentals.repository.CustomerRepository;
import com.rentals.quickrentals.repository.QuickRentalRepository;
import com.rentals.quickrentals.repository.VehicleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class QuickVehicleServiceImpl implements QuickVehicleService{

	@Autowired
	private CustomerRepository customerRepository ;
	@Autowired
	private QuickRentalRepository rentalRepository ;
	@Autowired
	private CustomerServiceImpl customerService ;
	@Autowired
	private VehicleRepository vehicleRepository ;
	
	private final ModelMapper modelMapper = new ModelMapper() ;
	


	@Override
	public List<RentalHistoryDTO> viewPickUpDateAndTimeAfter(LocalDateTime startTime) throws QuickRentalException {
		List<RentalHistory> rentalHistories = rentalRepository.findByPickUpDateTimeAfter(startTime) ;
		List<RentalHistoryDTO> rentalDTOHistories = rentalHistories.stream().map((rentalHistory)->{
			RentalHistoryDTO rentalDTO = modelMapper.map(rentalHistory, RentalHistoryDTO.class) ;
			VehicleDTO vehcleDTO = modelMapper.map(rentalHistory.getVehicle(), VehicleDTO.class) ;
			rentalDTO.setVehicle(vehcleDTO) ;
			return rentalDTO ;
		}).toList() ;
		
		return rentalDTOHistories ;
	}

	@Override
	public List<RentalHistoryDTO> viewDropOffDateAndTimeAfter(LocalDateTime startTime) throws QuickRentalException {
		List<RentalHistory> rentalHistories = rentalRepository.findByDropOffDateTimeAfter(startTime) ;
		List<RentalHistoryDTO> rentalDTOHistories = rentalHistories.stream().map((rentalHistory)->{
			RentalHistoryDTO rentalDTO = modelMapper.map(rentalHistory, RentalHistoryDTO.class) ;
			VehicleDTO vehcleDTO = modelMapper.map(rentalHistory.getVehicle(), VehicleDTO.class) ;
			rentalDTO.setVehicle(vehcleDTO) ;
			return rentalDTO ;
		}).toList() ;
		
		return rentalDTOHistories ;
	}

	@Override
	public List<RentalHistoryDTO> viewPickUpDateAndTimeBetween(LocalDateTime startTime, LocalDateTime endTime)
			throws QuickRentalException {
		List<RentalHistory> rentalHistories = rentalRepository.findByPickUpDateTimeBetween(startTime, endTime) ;
		List<RentalHistoryDTO> rentalDTOHistories = rentalHistories.stream().map((rentalHistory)->{
			RentalHistoryDTO rentalDTO = modelMapper.map(rentalHistory, RentalHistoryDTO.class) ;
			VehicleDTO vehcleDTO = modelMapper.map(rentalHistory.getVehicle(), VehicleDTO.class) ;
			rentalDTO.setVehicle(vehcleDTO) ;
			return rentalDTO ;
		}).toList() ;
		
		return rentalDTOHistories ;
	}

	
	@Override
	public List<RentalHistoryDTO> viewDropOffDateAndTimeBetween(LocalDateTime startTime, LocalDateTime endTime)
			throws QuickRentalException {
		
		List<RentalHistory> rentalHistories = rentalRepository.findByDropOffDateTimeBetween(startTime, endTime) ;
		List<RentalHistoryDTO> rentalDTOHistories = rentalHistories.stream().map((rentalHistory)->{
			RentalHistoryDTO rentalDTO = modelMapper.map(rentalHistory, RentalHistoryDTO.class) ;
			VehicleDTO vehcleDTO = modelMapper.map(rentalHistory.getVehicle(), VehicleDTO.class) ;
			rentalDTO.setVehicle(vehcleDTO) ;
			return rentalDTO ;
		}).toList() ;
		
		return rentalDTOHistories ;
	}



	@Override
	public String bookMyBike(CustomerDTO customerDTO, RentalHistoryDTO rental) throws QuickRentalException {
		
		Optional<Customer> optional = customerRepository.findByDrivingLicenceNo(customerDTO.getDrivingLicenceNo()) ;
		
		Customer customer = optional.orElseThrow(()-> new QuickRentalException("CustomerException.CUSTOMER_NOT_FOUND")) ;
		
		Optional<Vehicle> optionalVehicle = vehicleRepository.findByVehicleNo(rental.getVehicle().getVehicleNo()) ;
		Vehicle vehicle = optionalVehicle.orElseThrow(()-> new QuickRentalException("QuickVehicleException.VEHICLE_NOT_AVAILABLE")) ;
		
		if( vehicle.getBookingStatus() != VehicleTypeAndStatus.AVAILABLE )
			throw new QuickRentalException("QuickVehicleException.VEHICLE_BOOKING_NOT_AVAILABLE") ;
		
		vehicle.setBookingStatus(VehicleTypeAndStatus.BOOKED);
		
		RentalHistory rentalHistory = modelMapper.map(rental, RentalHistory.class) ;
		rentalHistory.setVehicle(vehicle);
		rentalHistory.setCustomer(customer);
		
		rentalRepository.save(rentalHistory) ;
		
		customer.getRentalHistories().add(rentalHistory) ;
		return rentalHistory.getVehicle().getVehicleNo() ;
	}



	@Override
	public List<RentalHistoryDTO> viewRentalHistory(String vehicleNo) throws QuickRentalException {
		List<RentalHistory> rentalHistory = rentalRepository.findByVehicle_VehicleNo(vehicleNo ) ;
		List<RentalHistoryDTO> rentalHistoryDTOs = rentalHistory.stream().map(rental ->{
			RentalHistoryDTO rentalDTO = modelMapper.map(rental, RentalHistoryDTO.class) ;
			VehicleDTO vehicleDTO = modelMapper.map(rental.getVehicle(), VehicleDTO.class) ;
			rentalDTO.setVehicle(vehicleDTO) ;
			return rentalDTO ;
		}).sorted((rentalHistoryObj1 , rentalHistoryObj2)-> 
		rentalHistoryObj1.getPickUpDateTime().isBefore(rentalHistoryObj2.getPickUpDateTime()) ? -1 :1 )
		.toList() ;
		return rentalHistoryDTOs ;
	}

	@Override
	public List<RentalHistoryDTO> viewRentalHistory(String drivingLicence, int page, int size)
			throws QuickRentalException {
		Sort sort = Sort.by("pickUpDateTime").descending() ;
		Pageable pageRecoreds = PageRequest.of(page , size , sort) ;
		Page<RentalHistory> rentalPage = rentalRepository.findByCustomerDrivingLicenceNo(drivingLicence, pageRecoreds ) ;
		
		List<RentalHistory> rentalHistory = rentalPage.getContent() ;
		
		List<RentalHistoryDTO> rentalHistoryDTOs = rentalHistory.stream().map(rental ->{
			RentalHistoryDTO rentalDTO = modelMapper.map(rental, RentalHistoryDTO.class) ;
			VehicleDTO vehicleDTO = modelMapper.map(rental.getVehicle(), VehicleDTO.class) ;
			rentalDTO.setVehicle(vehicleDTO) ;
			return rentalDTO ;
		}).toList() ;
		
		return rentalHistoryDTOs ;
	}

	
	
	
	

}
