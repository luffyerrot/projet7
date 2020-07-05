package fr.pierre.apirest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pierre.apirest.entities.Book;
import fr.pierre.apirest.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;
	
	Logger logger = LoggerFactory.getLogger(BookService.class);

	public Book getByIbn(Long ibn) {
		this.logger.debug("getByIbn Call = " + ibn);
		Book book = bookRepository.findById(ibn).get();
		this.logger.debug("getByIbn Return = " + book);
		return book;
	}
	
	public List<Book> getByAuthor(String author) {
		this.logger.debug("getByAuthor Call = " + author);
		List<Book> book = bookRepository.findByAuthor(author);
		this.logger.debug("getByAuthor Return = " + book);
		return book;
	}

	public Book save(Book book) {
		this.logger.debug("save Call = " + book);
		Book bookreturn = bookRepository.save(book);
		this.logger.debug("save Return = " + bookreturn);
		return bookreturn;
	}
	
	public void delete(Long ibn) {
		this.logger.debug("delete Call = " + ibn);
		bookRepository.deleteById(ibn);
	}

	public List<Book> findAll() {
		List<Book> book = bookRepository.findAll();
		this.logger.debug("findAll Return = " + book);
		return book;
	}
}
