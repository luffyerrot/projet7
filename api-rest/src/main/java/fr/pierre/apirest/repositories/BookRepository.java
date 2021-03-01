package fr.pierre.apirest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.pierre.apirest.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	public List<Book> findByAuthorAndTitle(String author, String title);
	
	public List<Book> findByAuthor(String author);
	
	public List<Book> findByTitle(String title);
	
	public List<Book> findByPublisher(String publisher);
}
