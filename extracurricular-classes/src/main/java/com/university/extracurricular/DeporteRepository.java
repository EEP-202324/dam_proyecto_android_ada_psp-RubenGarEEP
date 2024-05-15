package com.university.extracurricular;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeporteRepository extends JpaRepository<Deporte, Long> {
    Deporte findByNombre(String nombre);
}
