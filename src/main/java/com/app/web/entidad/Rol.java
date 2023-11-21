package com.app.web.entidad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {

	@Id
	private int id;
	private String nombre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Rol(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Rol() {
		super();
	}

	public Rol(String nombre) {
		super();
		this.nombre = nombre;
	}

	
}
