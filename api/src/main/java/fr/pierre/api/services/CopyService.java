package fr.pierre.api.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.pierre.apirest.entities.Book;
import fr.pierre.apirest.entities.Copy;
import fr.pierre.apirest.entities.InitCopy;

@Service
public class CopyService {
	
	@Autowired
	private Environment environment;
	
	private InitCopy init = new InitCopy();
	private RestTemplate restTemplate = new RestTemplate();

	public Copy getCopyId(Long id)
	{
	    final String uri = environment.getRequiredProperty("copy.url") + id;
	     
	    String result = restTemplate.getForObject(uri, String.class);
		JSONObject json = new JSONObject(result);
		Copy copy = null;
		copy = init.toObject(json);
	    return copy;
	}
	
	public List<Copy> getAllCopy()
	{
	    final String uri = environment.getRequiredProperty("copy.url");
	     
	    String result = restTemplate.getForObject(uri, String.class);
	    JSONArray arrayJson = new JSONArray(result);
	    List<Copy> copies = new ArrayList();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
	    	copies.add(init.toObject(json));
	    }
	    return copies;
	}
	
	public void create(Copy copy, Book book)
	{
		final String uri = environment.getRequiredProperty("copy.url") + "save";
		copy.setBook(book);
		restTemplate.put(uri, copy);
	}
	
	public Copy update(Copy copy, Long id)
	{
		final String uri = environment.getRequiredProperty("copy.url") + "update/" + id;
		String result = restTemplate.postForObject(uri, copy, String.class);
		JSONObject json = new JSONObject(result);
		Copy copy1 = null;
		copy1 = init.toObject(json);
	    return copy1;
	}
	
	public void delete(Long id)
	{
		final String uri = environment.getRequiredProperty("copy.url") + "delete/" + id;
		restTemplate.delete(uri);
	}
}
