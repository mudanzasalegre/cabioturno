package org.mudanzasalegre.cabioTurno.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfiles")
public final class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String info;

	public Perfil() {
		// Constructor vacío por defecto
	}

	public Perfil(Integer iD) {
		// Constructor vacío por defecto
		this.id = iD;
		this.nombre = "Nombre por defecto";
		this.info = "Info por defecto";
	}
	
	public Perfil(String perfil) {
		this.nombre = perfil;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String perfil) {
		this.nombre = perfil;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Perfil [id=" + id + ", nombre=" + nombre + ", info=" + info + "]";
	}


}
