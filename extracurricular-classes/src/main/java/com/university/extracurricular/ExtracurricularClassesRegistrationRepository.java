package com.university.extracurricular;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtracurricularClassesRegistrationRepository extends JpaRepository<ExtracurricularClassesRegistration, Long> {
    List<ExtracurricularClassesRegistration> findByNombreAndDeporteNombre(String nombre, String deporteNombre);
    Page<ExtracurricularClassesRegistration> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}

