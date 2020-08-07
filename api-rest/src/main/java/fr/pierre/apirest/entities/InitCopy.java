package fr.pierre.apirest.entities;

import org.json.JSONObject;

public class InitCopy {

	public Copy toObject(JSONObject json) {
		Copy copy = new Copy();
		copy.setBook(null);
		copy.setBookings(null);
		copy.setId(json.getLong("id"));
		copy.setAvailable(json.getInt("available"));
		return copy;
	}
	
	public JSONObject toJson(Copy copy) {
		JSONObject json = new JSONObject();
		json.put("book", "");
		json.put("booking", "");
		json.put("id", copy.getId());
		json.put("available", copy.getAvailable());
		return json;
	}
}
