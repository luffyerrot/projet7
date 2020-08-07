package fr.pierre.apirest.entities;

import org.json.JSONObject;

public class InitUser {

	public User toObject(JSONObject json) {
		User user = new User();
		user.setBookings(null);
		user.setRoles(null);
		user.setId(json.getLong("id"));
		user.setUsername(json.getString("username"));
		user.setEmail(json.getString("email"));
		user.setPassword(json.getString("password"));
		return user;
	}
	
	public JSONObject toJson(User user) {
		JSONObject json = new JSONObject();
		json.put("role", "");
		json.put("booking", "");
		json.put("id", user.getId());
		json.put("username", user.getUsername());
		json.put("email", user.getEmail());
		json.put("password", user.getPassword());
		return json;
	}
}
