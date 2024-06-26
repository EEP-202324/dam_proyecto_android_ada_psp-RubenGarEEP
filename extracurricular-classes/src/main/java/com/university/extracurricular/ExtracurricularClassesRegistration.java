package com.university.extracurricular;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "registros")
public class ExtracurricularClassesRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int edad;
    private String horario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deporte_id", nullable = false)
    @JsonBackReference
    private Deporte deporte;

   
    public ExtracurricularClassesRegistration() {}

    // Constructor con parámetros
    public ExtracurricularClassesRegistration(String nombre, int edad, String horario, Deporte deporte) {
        this.nombre = nombre;
        this.edad = edad;
        this.horario = horario;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
}
