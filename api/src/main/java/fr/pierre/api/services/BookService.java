package fr.pierre.api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.pierre.api.entities.Book;
import fr.pierre.api.entities.InitBook;

@Service
public class BookService {
	
	@Autowired
	private Environment environement;
	
	private InitBook init = new InitBook();
	private RestTemplate restTemplate = new RestTemplate();

	public Book getBookByIbn(Long ibn)
	{
	    final String uri = environement.getRequiredProperty("book.url") + ibn;
	     
	    String result = restTemplate.getForObject(uri, String.class);
		JSONObject json = new JSONObject(result);
		Book book = null;
	    try {
			book = init.toObject(json);
		} catch (ParseException | JSONException e) {
			e.printStackTrace();
		}
	    return book;
	}
	
	public List<Book> getAllBook()
	{	     
	    final String uri = environement.getRequiredProperty("book.url");
	    
	    String result = restTemplate.getForObject(uri, String.class);
	    JSONArray arrayJson = new JSONArray(result);
	    List<Book> books = new ArrayList<>();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
	    	try {
		    	books.add(init.toObject(json));
			} catch (ParseException | JSONException e) {
				e.printStackTrace();
			}
	    }
	    return books;
	}
	
	public List<Book> getByAuthorAndTitle(String author, String title)
	{	     
	    final String uri = environement.getRequiredProperty("book.url") + "/authorandtitle/" + author + "/" + title;
	    
	    String result = restTemplate.getForObject(uri, String.class);
	    JSONArray arrayJson = new JSONArray(result);
	    List<Book> books = new ArrayList<>();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
	    	try {
		    	books.add(init.toObject(json));
			} catch (ParseException | JSONException e) {
				e.printStackTrace();
			}
	    }
	    return books;
	}
	
	public List<Book> getByAuthor(String author)
	{	     
	    final String uri = environement.getRequiredProperty("book.url") + "/author/" + author;
	    
	    String result = restTemplate.getForObject(uri, String.class);
	    JSONArray arrayJson = new JSONArray(result);
	    List<Book> books = new ArrayList<>();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
	    	try {
		    	books.add(init.toObject(json));
			} catch (ParseException | JSONException e) {
				e.printStackTrace();
			}
	    }
	    return books;
	}
	
	public List<Book> getByTitle(String title)
	{	     
	    final String uri = environement.getRequiredProperty("book.url") + "/title/" + title;
	    
	    String result = restTemplate.getForObject(uri, String.class, title);
	    JSONArray arrayJson = new JSONArray(result);
	    List<Book> books = new ArrayList<>();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
	    	try {
		    	books.add(init.toObject(json));
			} catch (ParseException | JSONException e) {
				e.printStackTrace();
			}
	    }
	    return books;
	}
	
	public void create(Book book, String date)
	{
		final String uri = environement.getRequiredProperty("book.url") + "save";
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			book.setRelease_date(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		restTemplate.put(uri, book);
	}
	
	public void update(Book book, Long ibn)
	{
		final String uri = environement.getRequiredProperty("book.url") + "update/" + ibn;
		restTemplate.postForObject(uri, book, String.class);
	}
	
	public void delete(Long ibn)
	{
		final String uri = environement.getRequiredProperty("book.url") + "delete/" + ibn;
		restTemplate.delete(uri);
	}
}
