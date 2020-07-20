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

import fr.pierre.apirest.entities.Book;
import fr.pierre.apirest.services.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	BookService bookService;
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAll() {
		return ResponseEntity.ok(bookService.findAll());
	}
	
	@GetMapping("/{ibn}")
	public ResponseEntity<Book> getByIbn(@PathVariable Long ibn) {
		Book bookByIbn = bookService.getByIbn(ibn);
		if (bookByIbn != null) {
			return ResponseEntity.ok(bookByIbn);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/save")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.save(book));
	}
	
	@PostMapping("/update/{ibn}")
	public ResponseEntity<Book> updateBook(@PathVariable Long ibn, @RequestBody Book book) {
		Book bookByIbn = bookService.getByIbn(ibn);
		if (bookByIbn != null && book.getIbn() == bookByIbn.getIbn()) {
			return ResponseEntity.ok(bookService.save(bookByIbn));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/delete/{ibn}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long ibn) {
		bookService.delete(ibn);
		return ResponseEntity.ok().build();
	}
}
