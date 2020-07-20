package fr.pierre.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.pierre.apirest.entities.Copy;
import fr.pierre.apirest.services.CopyService;

@RestController
@RequestMapping(value = "/copy")
public class CopyController {

	@Autowired
	CopyService copyService;
	
	@GetMapping("/")
	public ResponseEntity<List<Copy>> getAll() {
		return ResponseEntity.ok(copyService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Copy> getById(@PathVariable Long id) {
		Copy copyById = copyService.getById(id);
		if (copyById != null) {
			return ResponseEntity.ok(copyById);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("save")
	public ResponseEntity<Copy> saveCopy(@RequestBody Copy copy) {
		return ResponseEntity.ok(copyService.save(copy));
	}
	
	@PostMapping("update/{id}")
	public ResponseEntity<Copy> updateCopy(@PathVariable Long id, @RequestBody Copy copy) {
		Copy copyById = copyService.findById(id);
		if (copyById != null && copy.getId() == copyById.getId()) {
			return ResponseEntity.ok(copyService.save(copy));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteCopy(@PathVariable Long id) {
		copyService.delete(id);
		return ResponseEntity.ok().build();
	}
}