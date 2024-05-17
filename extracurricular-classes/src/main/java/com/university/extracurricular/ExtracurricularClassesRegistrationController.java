 package com.university.extracurricular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
public class ExtracurricularClassesRegistrationController {

    @Autowired
   
    private ExtracurricularClassesRegistrationRepository registrationRepository;

    @Autowired
    private DeporteRepository deporteRepository;

    @PostMapping
    public ResponseEntity<ExtracurricularClassesRegistration> createRegistration(@RequestBody ExtracurricularClassesRegistration registration) {
        try {
            Deporte deporte = registration.getDeporte();
            Optional<Deporte> existingDeporte = deporteRepository.findByNombre(deporte.getNombre());

            if (existingDeporte.isPresent()) {
                registration.setDeporte(existingDeporte.get());
            } else {
                Deporte savedDeporte = deporteRepository.save(deporte);
                registration.setDeporte(savedDeporte);
            }

            ExtracurricularClassesRegistration savedRegistration = registrationRepository.save(registration);
            return new ResponseEntity<>(savedRegistration, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteRegistrationByNameAndDeporte(@RequestParam String nombre, @RequestParam String deporte) {
         try {
            List<ExtracurricularClassesRegistration> registrations = registrationRepository.findByNombreAndDeporteNombre(nombre, deporte);
            if (registrations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            for (ExtracurricularClassesRegistration registration : registrations) {
                registrationRepository.delete(registration);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       }
        @GetMapping
        public ResponseEntity<List<ExtracurricularClassesRegistration>> getAllRegistrations() {
           try {
                List<ExtracurricularClassesRegistration> registrations = registrationRepository.findAll();
                return new ResponseEntity<>(registrations, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @PutMapping("/{id}")
        public ResponseEntity<ExtracurricularClassesRegistration> updateRegistration(
                @PathVariable Long id, @RequestBody ExtracurricularClassesRegistration updatedRegistration) {
            
            try {
                return registrationRepository.findById(id)
                        .map(existingRegistration -> {
                            existingRegistration.setNombre(updatedRegistration.getNombre());
                            existingRegistration.setEdad(updatedRegistration.getEdad());
                            existingRegistration.setHorario(updatedRegistration.getHorario());
                            existingRegistration.setDeporte(updatedRegistration.getDeporte());
                            ExtracurricularClassesRegistration savedRegistration = registrationRepository.save(existingRegistration);
                            return new ResponseEntity<>(savedRegistration, HttpStatus.OK);
                        })
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @GetMapping("/search")
        public ResponseEntity<Page<ExtracurricularClassesRegistration>> searchRegistrations(
                
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
        ) {
            try {
                PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
                Page<ExtracurricularClassesRegistration> registrations = registrationRepository.findByNombreContainingIgnoreCase(nombre, pageable);
                return ResponseEntity.ok(registrations);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }