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
import fr.pierre.apirest.entities.InitBook;
import fr.pierre.apirest.services.BookService;
import fr.pierre.apirest.services.CopyService;

@RestController
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	BookService bookService;
	@Autowired
	CopyService copyService;
	
	InitBook init = new InitBook();
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAll() {
		List<Book> books = bookService.findAll();
		for (int i = 0; i < books.size(); i++) {

			for (int j = 0; j < books.get(i).getCopies().size(); j++) {
				books.get(i).getCopies().get(j).setBook(null);
				books.get(i).getCopies().get(j).setBookings(null);
			}
		}
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("/{ibn}")
	public ResponseEntity<Book> getByIbn(@PathVariable Long ibn) {
		Book bookByIbn = bookService.getByIbn(ibn);
		if (bookByIbn != null) {
			for (int i = 0; i < bookByIbn.getCopies().size(); i++) {
				bookByIbn.getCopies().get(i).setBook(null);
				bookByIbn.getCopies().get(i).setBookings(null);
			}
			return ResponseEntity.ok(bookByIbn);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/save")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {

		Book book1 = bookService.create(book);
		return ResponseEntity.ok(book1);
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
