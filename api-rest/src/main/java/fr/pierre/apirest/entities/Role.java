package fr.pierre.apirest.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements Serializable{

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	@Column(nullable = false, unique = true)
    private String name;
    
	//---------------------------------------------------------------------------------
	
	@ManyToMany(mappedBy = "roles")
    private List <User> users;

	//---------------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
