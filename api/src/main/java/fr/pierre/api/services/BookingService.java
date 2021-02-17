package fr.pierre.api.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.pierre.apirest.entities.Booking;
import fr.pierre.apirest.entities.Copy;
import fr.pierre.apirest.entities.InitBooking;
import fr.pierre.apirest.entities.InitCopy;
import fr.pierre.apirest.entities.InitUser;
import fr.pierre.apirest.entities.User;

@Service
public class BookingService {

	@Autowired
	private Environment environment;
	
	private InitBooking init = new InitBooking();
	private InitCopy initCopy = new InitCopy();
	private InitUser initUser = new InitUser();
	private RestTemplate restTemplate = new RestTemplate();

	public Booking getBookingId(Long id)
	{
	    final String uri = environment.getRequiredProperty("booking.url") + id;
	     
	    String result = restTemplate.getForObject(uri, String.class);
		JSONObject json = new JSONObject(result);
		Booking booking = null;
		try {
			booking = init.toObject(json);
		} catch (ParseException | JSONException e) {
			e.printStackTrace();
		}
	    return booking;
	}
	
	public List<Booking> getByUserId()
	{
		if(userAuth() != null) {
			Long id = userAuth().getId();
		    final String uri = environment.getRequiredProperty("booking.url") + "userid/" + id;
		    String result = restTemplate.getForObject(uri, String.class);
			JSONArray jsonArray = new JSONArray(result);
			
			List<Booking> bookings = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Booking booking;
				try {
					booking = init.toObject(json);
					bookings.add(booking);
				} catch (JSONException | ParseException e) {
					e.printStackTrace();
				}
			}
		    return bookings;
		} else {
			return null;
		}
	}
	
	public List<Booking> getAllBooking()
	{
	    final String uri = environment.getRequiredProperty("booking.url");
	     
	    String result = restTemplate.getForObject(uri, String.class);
	    JSONArray arrayJson = new JSONArray(result);
	    List<Booking> bookings = new ArrayList<>();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
	    	try {
				bookings.add(init.toObject(json));
			} catch (ParseException | JSONException e) {
				e.printStackTrace();
			}
	    }
	    return bookings;
	}
	
	public User userAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		if (name == "anonymousUser") {
			return null;
		}
		
		final String uriUser = environment.getRequiredProperty("user.url") + "find/" + name;
		
	    String resultUser = restTemplate.getForObject(uriUser, String.class);

		JSONObject jsonU = new JSONObject(resultUser);
		User user = initUser.toObject(jsonU);
		
		return user;
	}
	
	public List<Long> userBookingCopyId() {

		final String uriBooking = environment.getRequiredProperty("booking.url") + "userid/" + userAuth().getId();
		String resultBooking = restTemplate.getForObject(uriBooking, String.class);
		JSONArray jsonBooking = new JSONArray(resultBooking);
		List<Long> userCopyId = new ArrayList<>();
		for (Object o: jsonBooking) {
			Long idCopy = ((JSONObject)o).getJSONObject("copy").getLong("id");
			userCopyId.add(idCopy);
		}
		return userCopyId;
	}
	
	public ResponseEntity<Void> create(Long id)
	{
		final String uri = environment.getRequiredProperty("booking.url") + "save";
		final String uriBooking = environment.getRequiredProperty("booking.url") + "userid/" + userAuth().getId();
		final String uriCopy = environment.getRequiredProperty("copy.url") + id;
		
		String resultBooking = restTemplate.getForObject(uriBooking, String.class);
		JSONArray jsonB = new JSONArray(resultBooking);
		List<Long> userCopyId = new ArrayList<>();
		for (Object o: jsonB) {
			Long idCopy = ((JSONObject)o).getJSONObject("copy").getLong("id");
			userCopyId.add(idCopy);
		}
		if (userCopyId.contains(id)) {
			return ResponseEntity.badRequest().build();
		}
		
	    String resultCopy = restTemplate.getForObject(uriCopy, String.class);
	    
		JSONObject jsonC = new JSONObject(resultCopy);
		Copy copy = initCopy.toObject(jsonC);
		
		Booking booking = new Booking();
		booking.setCopy(copy);
		booking.setUser(userAuth());
		
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -15);
		date = c.getTime();
		
		booking.setBooking_date(date);
		booking.setDelay(false);
		booking.setRendering(false);
		booking.setRecall(0);
		restTemplate.put(uri, booking);
		return ResponseEntity.ok().build();
	}
	
	public Booking update(Booking booking, Long id)
	{
		final String uri = environment.getRequiredProperty("booking.url") + "update/" + id;
		String result = restTemplate.postForObject(uri, booking, String.class);
		JSONObject json = new JSONObject(result);
		Booking booking1 = null;
		try {
			booking1 = init.toObject(json);
		} catch (ParseException | JSONException e) {
			e.printStackTrace();
		}
	    return booking1;
	}
	
	public void delete(Long id)
	{
		final String uri = environment.getRequiredProperty("booking.url") + "delete/" + id;
		restTemplate.delete(uri);
	}
}
