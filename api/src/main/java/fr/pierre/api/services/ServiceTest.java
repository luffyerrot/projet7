package fr.pierre.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceTest {

	public String getBookByIbn(Long ibn)
	{
	    final String uri = "http://localhost:8080/book/" + ibn;
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    return result;
	}
	
	public String getAllBook()
	{
	    final String uri = "http://localhost:8080/book/";
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    return result;
	}
}
