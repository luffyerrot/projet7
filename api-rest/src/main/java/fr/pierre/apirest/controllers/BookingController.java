package fr.pierre.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.pierre.apirest.entities.Booking;
import fr.pierre.apirest.entities.Copy;
import fr.pierre.apirest.entities.User;
import fr.pierre.apirest.services.BookingService;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;
	
	@GetMapping("/")
	public ResponseEntity<List<Booking>> getAllNotRendered() {
		return ResponseEntity.ok(bookingService.findAllNotRendered());
	}
	
	@GetMapping("/rendered")
	public ResponseEntity<List<Booking>> getAllRendered() {
		return ResponseEntity.ok(bookingService.findAllRendered());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Booking> getById(@PathVariable Long id) {
		Booking bookingById = bookingService.getById(id);
		if (bookingById != null) {
			return ResponseEntity.ok(bookingById);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/userid/{id}")
	public ResponseEntity<List<Booking>> getByUserId(@PathVariable Long id) {
		List<Booking> bookingByUserId = bookingService.getByUserId(id);
		if (bookingByUserId != null) {
			bookingByUserId.forEach(booking->booking.setCopy(new Copy(booking.getCopy().getId(), booking.getCopy().getEtat(), booking.getCopy().getBook().getAuthor(), booking.getCopy().getBook().getPublisher(), booking.getCopy().getBook().getTitle(), booking.getCopy().getBook().getIbn())));
			bookingByUserId.forEach(booking->booking.setUser(new User(booking.getUser().getId(), booking.getUser().getEmail(), booking.getUser().getUsername())));
			return ResponseEntity.ok(bookingByUserId);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/save")
	public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking) {
		return ResponseEntity.ok(bookingService.save(booking));
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
		Booking bookingById = bookingService.getById(id);
		if (bookingById != null && booking.getId() == bookingById.getId()) {
			return ResponseEntity.ok(bookingService.save(bookingById));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/updateRecall/{id}")
	public void updateRecall(@PathVariable Long id, @RequestBody Booking booking) {
		Booking bookingById = bookingService.getById(id);
		if (bookingById != null && booking.getId() == bookingById.getId()) {
			bookingById.setRecall(booking.getRecall());
			bookingService.save(bookingById);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/getdate")
	public ResponseEntity<List<Booking>> getWithDate() {
		List<Booking> bookingByDateAndDelay = bookingService.findAllByDate();
		bookingByDateAndDelay.forEach(booking->booking.setCopy(new Copy(booking.getCopy().getId(), booking.getCopy().getEtat(), booking.getCopy().getBook().getAuthor(), booking.getCopy().getBook().getPublisher(), booking.getCopy().getBook().getTitle(), booking.getCopy().getBook().getIbn())));
		bookingByDateAndDelay.forEach(booking->booking.setUser(new User(booking.getUser().getId(), booking.getUser().getEmail(), booking.getUser().getUsername())));
		return ResponseEntity.ok(bookingByDateAndDelay);
	}
	
	@PutMapping("/extend/{id}")
	public void extend(@PathVariable Long id) {
		Booking booking = bookingService.getById(id);
		if (booking.getDelay() == false) {
			bookingService.changeDelay(booking, true);
			bookingService.extend(booking);
		}
	}
	
	@PutMapping("/rendering/{id}")
	public void rendering(@PathVariable Long id) {
		Booking booking = bookingService.getById(id);
		bookingService.changeRendering(booking, true);
	}
}
