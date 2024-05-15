package com.university.extracurricular;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExtracurricularClassesRegistrationRepository extends JpaRepository<ExtracurricularClassesRegistration, Long> {
    Page<ExtracurricularClassesRegistration> findByNombreContaining(String nombre, Pageable pageable);
    Page<ExtracurricularClassesRegistration> findByDeporteNombreContaining(String deporte, Pageable pageable);
    Page<ExtracurricularClassesRegistration> findByNombreContainingAndDeporteNombreContaining(String nombre, String deporte, Pageable pageable);
    @Query("SELECT e FROM ExtracurricularClassesRegistration e WHERE e.nombre = :nombre AND e.deporte.nombre = :deporteNombre")
    List<ExtracurricularClassesRegistration> findByNombreAndDeporteNombre(@Param("nombre") String nombre, @Param("deporteNombre") String deporteNombre);
}
