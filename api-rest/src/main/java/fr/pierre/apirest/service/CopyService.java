package fr.pierre.apirest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.pierre.apirest.entities.Copy;
import fr.pierre.apirest.repositories.CopyRepository;

@Service
public class CopyService {
	
	@Autowired
	CopyRepository copyRepository;
	
	Logger logger = LoggerFactory.getLogger(CopyService.class);

	public Copy getById(Long id) {
		this.logger.debug("getById Call = " + id);
		Copy copy = copyRepository.findById(id).get();
		this.logger.debug("getById Return = " + copy);
		return copy;
	}

	public Copy save(Copy copy) {
		this.logger.debug("save Call = " + copy);
		Copy copyreturn = copyRepository.save(copy);
		this.logger.debug("save Return = " + copyreturn);
		return copyreturn;
	}
	
	public void delete(Long id) {
		this.logger.debug("delete Call = " + id);
		copyRepository.deleteById(id);
	}

	public List<Copy> findAll() {
		List<Copy> copy = copyRepository.findAll();
		this.logger.debug("findAll Return = " + copy);
		return copy;
	}
	
	public Copy findById(Long id) {
		Copy copy = copyRepository.findById(id).get();
		this.logger.debug("findAll Return = " + copy);
		return copy;
	}
}
