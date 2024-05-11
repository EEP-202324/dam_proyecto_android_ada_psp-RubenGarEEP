package com.university.extracurricular;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "deporte")  // Asegúrate de que este nombre coincide con el de la tabla en la base de datos
public class Deporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "deporte")
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
