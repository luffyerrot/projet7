package fr.pierre.apirest.services;

import java.util.Calendar;
import java.util.Date;
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
	
	public void changeDelay(Booking booking, Boolean delay) {
		this.logger.info("changeDelay Call = " + booking + " " + delay);
		bookingRepository.changeDelay(booking.getId(), delay);
	}
	
	public void deleteById(Long idBooking) {
		this.logger.info("deleteById Call = " + idBooking);
		bookingRepository.deleteById(idBooking);
	}
	
	public void extend(Booking booking) {
		this.logger.info("extend Call = " + booking.toString());
		Date date = booking.getBooking_date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, + 30);
		date = c.getTime();
		bookingRepository.extend(booking.getId(), date);
	}
	
	public Boolean checkAcces(Long idBooking) {
		if (getByUserId(userService.authUser().getId()).contains(getById(idBooking))) {
			return true;
		} else {
			return false;
		}
	}

	public List<Booking> findAll() {
		List<Booking> bookings = bookingRepository.findAll();
		bookings.forEach(b -> b.setUser(null));
		bookings.forEach(booking->booking.setCopy(new Copy(booking.getCopy().getId(), booking.getCopy().getEtat(), booking.getCopy().getBook().getAuthor(), booking.getCopy().getBook().getPublisher(), booking.getCopy().getBook().getTitle(), booking.getCopy().getBook().getIbn())));
		this.logger.debug("findAll Return = " + bookings);
		return bookings;
	}
	
	public List<Booking> findAllByDate() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -30);
		date = c.getTime();
		List<Booking> bookings = bookingRepository.getByDate(date);
		return bookings;
	}
}