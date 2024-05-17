package com.university.extracurricular;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "deportes")
public class Deporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "deporte", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ExtracurricularClassesRegistration> registrations;

    // Constructor por defecto
    public Deporte() {}

    // Constructor con parámetros
    public Deporte(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ExtracurricularClassesRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<ExtracurricularClassesRegistration> registrations) {
        this.registrations = registrations;
    }
}
