package fr.pierre.apirest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.pierre.apirest.entities.Booking;
import fr.pierre.apirest.entities.Copy;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	@Modifying
	@Query("UPDATE Booking b SET b.rendering = :rendering WHERE b.copy = :copy")
	void changeRendering(@Param("copy")Copy copy, @Param("rendering")Boolean rendering);
	
	@Modifying
	@Query("UPDATE Booking b SET b.delay = :delay WHERE b.copy = :copy")
	void changeDelay(@Param("copy")Copy copy, @Param("delay")Boolean delay);
	
	@Modifying
	@Query("UPDATE Booking b SET b = :booking WHERE b.id = :id")
	public Booking update(@Param("booking")Booking booking, @Param("id")Long id);
	
	public List<Booking> findByUserId(Long id);
	
	public List<Booking> findByCopyId(Long idCopy);
}
