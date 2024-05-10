package com.university.extracurricular;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
@Entity
@Table(name = "registros")
public class ExtracurricularClassesRegistration {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String nombre;
    private int edad; 
    private String deporte;
	public ExtracurricularClassesRegistration(Long id, String nombre, int edad, String deporte) {
		super();
		Id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.deporte = deporte;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
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
	public String getDeporte() {
		return deporte;
	}
	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}
}