package fr.pierre.api.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.pierre.apirest.entities.InitUser;
import fr.pierre.apirest.entities.User;

@Service
public class UserService {

	@Autowired
	private Environment environment;
	
	private InitUser init = new InitUser();
	private RestTemplate restTemplate = new RestTemplate();

	public User getUserId(Long id)
	{
	    final String uri = environment.getRequiredProperty("user.url") + id;
	     
	    String result = restTemplate.getForObject(uri, String.class);
		JSONObject json = new JSONObject(result);
		User user = null;
		user = init.toObject(json);
	    return user;
	}
	
	public User getUserEmail(String mail)
	{
	    final String uri = environment.getRequiredProperty("user.url") + "find/" + mail;
	     
	    String result = restTemplate.getForObject(uri, String.class);
		User user = init.toObject((JSONObject)new JSONObject(result));
	    return user;
	}
	
	public List<User> getAllUser()
	{
	    final String uri = environment.getRequiredProperty("user.url");
	     
	    String result = restTemplate.getForObject(uri, String.class);
	    JSONArray arrayJson = new JSONArray(result);
	    List<User> users = new ArrayList();
	    for (int i = 0; i < arrayJson.length(); i++){
	    	JSONObject json = new JSONObject(arrayJson.get(i).toString());
	    	users.add(init.toObject(json));
	    }
	    return users;
	}
	
	public void create(User user)
	{
		final String uri = environment.getRequiredProperty("user.url") + "save";
		restTemplate.put(uri, user);
	}
	
	public User update(User user, Long id)
	{
		final String uri = environment.getRequiredProperty("user.url") + "update/" + id;
		String result = restTemplate.postForObject(uri, user, String.class);
		JSONObject json = new JSONObject(result);
		User user1 = null;
		user1 = init.toObject(json);
	    return user1;
	}
	
	public void delete(Long id)
	{
		final String uri = environment.getRequiredProperty("user.url") + "delete/" + id;
		restTemplate.delete(uri);
	}
}
