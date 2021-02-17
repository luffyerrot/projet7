package fr.pierre.batch.reader;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.client.RestTemplate;

import fr.pierre.apirest.entities.Booking;
import fr.pierre.apirest.entities.InitBooking;

public class BookingReader implements ItemReader<Booking>{
	
	private InitBooking init = new InitBooking();
	
	private int nextBookingIndex;
	private List<Booking> bookingData;

	@Override
	public Booking read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {			
		 	
		if (bookingDataIsNotInitialized()) {
	            bookingData = fetchBookingDataFromAPI();
	        }
	 
		 	Booking nextBooking = null;
	 
	        if (nextBookingIndex < bookingData.size()) {
	        	nextBooking = bookingData.get(nextBookingIndex);
	        	nextBookingIndex++;
	        } else {
	        	nextBookingIndex = 0;
	        }

			System.out.println("-------------------r " + nextBooking);
	        return nextBooking;
	}

    private boolean bookingDataIsNotInitialized() {
        return this.bookingData == null;
    }
 
    private List<Booking> fetchBookingDataFromAPI() {
		String uri = "http://localhost:8080/booking/getdate/";
		RestTemplate restTemplate = new RestTemplate();
    	String result = restTemplate.getForObject(uri, String.class);
    	JSONArray arrayJson = new JSONArray(result);
	    List<Booking> bookings = new ArrayList<>();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
			try {
				bookings.add(init.toObject(json));
			} catch (JSONException | java.text.ParseException e) {
				e.printStackTrace();
			}
	    }
			
        return bookingData = bookings;
    }
}
