package com.university.extracurricular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class ExtracurricularClassesRegistrationController {

    @Autowired
    private ExtracurricularClassesRegistrationRepository repository;

    @PostMapping
    public ResponseEntity<ExtracurricularClassesRegistration> createRegistration(@RequestBody ExtracurricularClassesRegistration registration) {
        ExtracurricularClassesRegistration savedRegistration = repository.save(registration);
        return new ResponseEntity<>(savedRegistration, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRegistrationByNameAndDeporte(@RequestParam String nombre, @RequestParam String deporte) {
        List<ExtracurricularClassesRegistration> registrations = repository.findByNombreAndDeporteNombre(nombre, deporte);
        if (registrations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (ExtracurricularClassesRegistration registration : registrations) {
            repository.delete(registration);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ExtracurricularClassesRegistration>> getAllRegistrations() {
        List<ExtracurricularClassesRegistration> registrations = repository.findAll();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtracurricularClassesRegistration> updateRegistration(
            @PathVariable Long id, @RequestBody ExtracurricularClassesRegistration updatedRegistration) {
        return repository.findById(id)
                .map(existingRegistration -> {
                    existingRegistration.setNombre(updatedRegistration.getNombre());
                    existingRegistration.setEdad(updatedRegistration.getEdad());
                    existingRegistration.setHorario(updatedRegistration.getHorario());
                    existingRegistration.setDeporte(updatedRegistration.getDeporte());
                    ExtracurricularClassesRegistration savedRegistration = repository.save(existingRegistration);
                    return new ResponseEntity<>(savedRegistration, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}


