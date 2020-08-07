package fr.pierre.apirest.entities;

import java.util.Date;

import org.json.JSONObject;

public class InitBooking {

	public Booking toObject(JSONObject json) {
		Booking booking = new Booking();
		booking.setCopy(null);
		booking.setUser(null);
		booking.setId(json.getLong("id"));
		booking.setBooking_date(new Date(json.getString("booking_date")));
		booking.setRendering(json.getBoolean("rendering"));
		booking.setDelay(json.getBoolean("delay"));
		return booking;
	}
	
	public JSONObject toJson(Booking booking) {
		JSONObject json = new JSONObject();
		json.put("copy", "");
		json.put("user", "");
		json.put("id", booking.getId());
		json.put("booking_date", booking.getBooking_date());
		json.put("rendering", booking.getRendering());
		json.put("delay", booking.getDelay());
		return json;
	}
}
