package fr.pierre.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.web.client.RestTemplate;

import fr.pierre.apirest.entities.Booking;

public class BookingWriter implements ItemWriter<Booking>{
	
	private RestTemplate restTemplate = new RestTemplate();
		
	@Override
	public void write(List<? extends Booking> items) throws Exception {
		
		for (int i = 0; i < items.size(); i++) {

			Booking booking = items.get(i);
			System.out.println("-------------------w " + booking.toString());
			final String uri = "http://localhost:8080/booking/updateRecall/" + booking.getId();
			restTemplate.postForObject(uri, booking, String.class);
		}
	}
}
