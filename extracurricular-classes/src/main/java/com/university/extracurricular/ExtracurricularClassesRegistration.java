package com.university.extracurricular;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "registros")  // Asegúrate de que este nombre coincide con el de la tabla en la base de datos
public class ExtracurricularClassesRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int edad;

    @ManyToOne
    @JoinColumn(name = "deporte_id", nullable = false)  // Asegúrate de que este nombre coincide con la columna en la tabla registros
    private Deporte deporte;

    // Constructor por defecto
    public ExtracurricularClassesRegistration() {}

    // Constructor con parámetros
    public ExtracurricularClassesRegistration(Long id, String nombre, int edad, Deporte deporte) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.deporte = deporte;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
}

