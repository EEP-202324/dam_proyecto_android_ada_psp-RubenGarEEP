package com.university.extracurricular;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtracurricularClassesRegistrationRepository extends JpaRepository<ExtracurricularClassesRegistration, Long> {
    Page<ExtracurricularClassesRegistration> findByNombreContaining(String nombre, Pageable pageable);
    Page<ExtracurricularClassesRegistration> findByDeporteNombreContaining(String deporte, Pageable pageable);
    Page<ExtracurricularClassesRegistration> findByNombreContainingAndDeporteNombreContaining(String nombre, String deporte, Pageable pageable);
}
