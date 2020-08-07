package fr.pierre.apirest.entities;

import org.json.JSONObject;

public class InitRole {

	public Role toObject(JSONObject json) {
		Role role = new Role();
		role.setUsers(null);
		role.setId(json.getLong("id"));
		role.setName(json.getString("name"));
		return role;
	}
	
	public JSONObject toJson(Role role) {
		JSONObject json = new JSONObject();
		json.put("user", "");
		json.put("id", role.getId());
		json.put("name", role.getName());
		return json;
	}
}
