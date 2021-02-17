package fr.pierre.apirest.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(nullable=false)
	private Date booking_date;
	
	@Column(nullable=false)
    private Boolean delay;
	
	@Column
	private int recall;

	@Column(nullable=false)
    private Boolean rendering;
	
	//---------------------------------------------------------------------------------

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
    private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "copy_id")
    private Copy copy;
	
	//---------------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Booking [id=" + id + ", booking_date=" + booking_date + ", delay=" + delay + ", recall=" + recall
				+ ", rendering=" + rendering + "]";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBooking_date() {
		return booking_date;
	}

	public void setBooking_date(Date booking_date) {
		this.booking_date = booking_date;
	}

	public Boolean getDelay() {
		return delay;
	}

	public void setDelay(Boolean delay) {
		this.delay = delay;
	}

	public int getRecall() {
		return recall;
	}

	public void setRecall(int recall) {
		this.recall = recall;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	
	public Boolean getRendering() {
		return rendering;
	}

	public void setRendering(Boolean rendering) {
		this.rendering = rendering;
	}

	public void setRecall(Integer recall) {
		this.recall = recall;
	}
}