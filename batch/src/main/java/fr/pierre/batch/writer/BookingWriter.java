package fr.pierre.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.web.client.RestTemplate;

import fr.pierre.apirest.entities.Booking;

public class BookingWriter implements ItemWriter<Booking>{

	private final String uri;
	private final RestTemplate restTemplate;
	
	public BookingWriter(String uri, RestTemplate restTemplate) {
		this.uri = uri;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void write(List<? extends Booking> items) throws Exception {
		for (Booking booking : items) {
			restTemplate.postForObject(uri + booking.getId(), booking, String.class);
		}
	}

}
