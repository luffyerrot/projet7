package fr.pierre.apirest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.pierre.apirest.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	public List<Book> findByAuthor(String author);
	
	public List<Book> findByPublisher(String publisher);
	
	public Optional<Book> findByTitle(String title);
}
