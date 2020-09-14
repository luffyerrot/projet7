package fr.pierre.apirest.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class InitBooking {

	public Booking toObject(JSONObject json) throws ParseException, JSONException {
		Booking booking = new Booking();
		JSONObject jsonC = json.getJSONObject("copy");
		JSONObject jsonB = jsonC.getJSONObject("book");
		booking.setCopy(new Copy(jsonC.getLong("id"), jsonC.getString("etat")));
		booking.getCopy().setBook(new Book(jsonB.getLong("ibn"), jsonB.getString("title"), jsonB.getString("author"), jsonB.getString("publisher")));
		booking.setUser(null);
		booking.setId(json.getLong("id"));
		String date = json.getString("booking_date").substring(0, 10);
		Date date1;
		date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		booking.setBooking_date(date1);
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
