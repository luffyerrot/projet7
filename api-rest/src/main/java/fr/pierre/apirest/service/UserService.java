package fr.pierre.apirest.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fr.pierre.apirest.entities.User;
import fr.pierre.apirest.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public User getById(Long id) {
		this.logger.debug("getById Call = " + id);
		if(id != 0) {
			User user = userRepository.findById(id).get();
			this.logger.debug("getById Return = " + user);
			return user;
		}
		return null;
	}

	public User save(User user) {
		this.logger.debug("save Call = " + user);
		User userreturn = userRepository.save(user);
		this.logger.debug("save Return = " + userreturn);
		return userreturn;
	}

	public List<User> findAll() {
		List<User> user = userRepository.findAll();
		this.logger.debug("findAll Return = " + user);
		return user;
	}

	public Long getIdByName(String name) {
		this.logger.debug("getIdByName Call = " + name);
		try {
			Long namereturn = userRepository.findByEmail(name).get().getId();
			this.logger.debug("getIdByName Return = " + namereturn);
			return namereturn;
		} catch (NoSuchElementException e) {
			Long noId = Integer.toUnsignedLong(0);
			this.logger.debug("getIdByName Try Catch Return = " + noId);
			return noId;
		}
	}

	public User authUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Long id = this.getIdByName(name);
		User user = this.getById(id);
		this.logger.debug("authUser Return = " + user);
		return user;
	}

	public User updateUser(User user) {
		this.logger.debug("updateUser Call = " + user);
		User userreturn = userRepository.updateUser(user.getId(), user.getUsername(), user.getEmail());
		this.logger.debug("updateUser Return = " + userreturn);
		return userreturn; 
	}
	
	public void deleteById(Long id) {
		this.logger.debug("deleteById Call = " + id);
		userRepository.deleteById(id);
	}
}
