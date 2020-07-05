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
import fr.pierre.apirest.service.BookingService;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;
	
	@GetMapping("/")
	public ResponseEntity<List<Booking>> getAll() {
		return ResponseEntity.ok(bookingService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Booking> getById(@PathVariable Long id) {
		Booking bookingById = bookingService.getById(id);
		if (bookingById != null) {
			return ResponseEntity.ok(bookingById);
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
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
