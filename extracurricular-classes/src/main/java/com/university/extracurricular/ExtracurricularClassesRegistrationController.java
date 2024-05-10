package com.university.extracurricular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class ExtracurricularClassesRegistrationController {
	 @Autowired
	    private ClassService classService;

	    @GetMapping
	    public List<Class> getAllClasses() {
	        return classService.findAll();
	    }

	    @PostMapping
	    public Class createClass(@RequestBody Class clazz) {
	        return classService.save(clazz);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Class> updateClass(@PathVariable Long id, @RequestBody Class classDetails) {
	        Class updatedClass = classService.updateClass(id, classDetails);
	        return ResponseEntity.ok(updatedClass);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
	        classService.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }

}
