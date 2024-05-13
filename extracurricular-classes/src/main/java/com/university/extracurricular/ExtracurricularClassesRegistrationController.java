package com.university.extracurricular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
public class ExtracurricularClassesRegistrationController {

    @Autowired
    private ExtracurricularClassesRegistrationRepository repository;

    // GET: Recuperar todos los registros con paginación, ordenación y filtrado
    @GetMapping
    public Page<ExtracurricularClassesRegistration> getRegistrations(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String deporte,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        
        if (nombre != null && deporte != null) {
            return repository.findByNombreContainingAndDeporteNombreContaining(nombre, deporte, pageable);
        } else if (nombre != null) {
            return repository.findByNombreContaining(nombre, pageable);
        } else if (deporte != null) {
            return repository.findByDeporteNombreContaining(deporte, pageable);
        } else {
            return repository.findAll(pageable);
        }
    }

    // POST: Crear un nuevo registro
    @PostMapping
    public ExtracurricularClassesRegistration createRegistration(@RequestBody ExtracurricularClassesRegistration registration) {
        return repository.save(registration);
    }

    // GET: Recuperar un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<ExtracurricularClassesRegistration> getRegistrationById(@PathVariable Long id) {
        Optional<ExtracurricularClassesRegistration> registration = repository.findById(id);
        if (registration.isPresent()) {
            return ResponseEntity.ok(registration.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT: Actualizar un registro existente
    @PutMapping("/{id}")
    public ResponseEntity<ExtracurricularClassesRegistration> updateRegistration(
            @PathVariable Long id, @RequestBody ExtracurricularClassesRegistration registrationDetails) {
        Optional<ExtracurricularClassesRegistration> optionalRegistration = repository.findById(id);
        if (optionalRegistration.isPresent()) {
            ExtracurricularClassesRegistration registration = optionalRegistration.get();
            registration.setNombre(registrationDetails.getNombre());
            registration.setEdad(registrationDetails.getEdad());
            registration.setDeporte(registrationDetails.getDeporte());
            ExtracurricularClassesRegistration updatedRegistration = repository.save(registration);
            return ResponseEntity.ok(updatedRegistration);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar un registro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        Optional<ExtracurricularClassesRegistration> optionalRegistration = repository.findById(id);
        if (optionalRegistration.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

