package fr.pierre.apirest.repositories;

import java.util.Date;
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
	@Query("UPDATE Booking b SET b.delay = :delay WHERE b.copy = :copy")
	void changeDelay(@Param("copy")Copy copy, @Param("delay")Boolean delay);
	
	@Modifying
	@Query("UPDATE Booking b SET b = :booking WHERE b.id = :id")
	public Booking update(@Param("booking")Booking booking, @Param("id")Long id);
	
	@Query("SELECT b FROM Booking b WHERE b.booking_date < :actualDate AND b.rendering = false")
	public List<Booking> getByDate(@Param("actualDate")Date actualDate);
	
	public List<Booking> findByUserId(Long id);
	
	public List<Booking> findByCopyId(Long idCopy);
}
