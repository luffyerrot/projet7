package fr.pierre.apirest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "copies")
public class Copy {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(nullable = false)
	private int available;
	
	//---------------------------------------------------------------------------------
	   
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name="user_role", joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")}, inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private List <Role> roles;
	
	@OneToMany(mappedBy = "copy")
    private List <Booking> bookings;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "book_ibn")
    private Book book;
	
    //----------------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Copy [id=" + id + ", available=" + available + "]";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}