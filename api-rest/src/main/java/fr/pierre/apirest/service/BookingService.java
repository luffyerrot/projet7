package fr.pierre.apirest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pierre.apirest.entities.Booking;
import fr.pierre.apirest.entities.Copy;
import fr.pierre.apirest.repositories.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	UserService userService;
	
	Logger logger = LoggerFactory.getLogger(BookingService.class);
	
	public Booking getById(Long id) {
		this.logger.info("getById Call = " + id);
		Booking booking =  bookingRepository.findById(id).get();
		this.logger.info("getById Return = " + booking);
		return booking;
	}
	
	public List<Booking> getByUserId(Long id) {
		this.logger.info("getByUserId Call = " + id);
		List<Booking> bookings = bookingRepository.findByUserId(id);
		this.logger.info("getByUserId Return = " + bookings);
		return bookings;
	}
	
	public Booking save(Booking booking) {
		this.logger.info("save Call = " + booking);
		Booking bookingreturn = bookingRepository.save(booking);
		this.logger.info("save Return = " + bookingreturn);
		return bookingreturn;
	}
	
	public Booking update(Booking booking) {
		this.logger.debug("update Call = " + booking);
		Booking bookingreturn = bookingRepository.update(booking, booking.getId());
		this.logger.debug("update Return = " + bookingreturn);
		return bookingreturn;
	}
	
	public List<Booking> getByCopyId(Long idCopy) {
		this.logger.info("getByCopyId Call = " + idCopy);
		List<Booking> bookings = bookingRepository.findByCopyId(idCopy);
		this.logger.info("getByCopyId Return = " + bookings);
		return bookings;
	}
	
	public void changeDelay(Copy copy, Boolean delay) {
		this.logger.info("changeDelay Call = " + copy.toString() + " " + delay);
		bookingRepository.changeDelay(copy, delay);
	}
	
	public void changeRendering(Copy copy, Boolean rendering) {
		this.logger.info("changeRendering Call = " + copy.toString() + " " + rendering);
		bookingRepository.changeRendering(copy, rendering);
	}
	
	public void deleteById(Long idBooking) {
		this.logger.info("deleteById Call = " + idBooking);
		bookingRepository.deleteById(idBooking);
	}
	
	public Boolean checkAcces(Long idBooking) {
		if (getByUserId(userService.authUser().getId()).contains(getById(idBooking))) {
			return true;
		} else {
			return false;
		}
	}

	public List<Booking> findAll() {
		List<Booking> booking = bookingRepository.findAll();
		this.logger.debug("findAll Return = " + booking);
		return booking;
	}
}