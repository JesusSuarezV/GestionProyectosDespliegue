package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // Etiqueta de Entidad
@Table(name = "proyecto") // Etiqueta que relaciona la tabla de la base de datos con su nombre"
public class Proyecto {
	@Id // Etiquet de llave primaria
	@Column(name = "Id") // Etiqueta de la columna con su nombre
	int id;
	@Column(name = "Nombre")
	String nombre;
	@Column(name = "ubicacion")
	String ubicacion;
	@Column(name = "Visibilidad")
	boolean visibilidad;

	// Getter And Setters
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

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	// Constructores
	public Proyecto(int id, String nombre, String ubicacion, boolean visibilidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.visibilidad = visibilidad;
	}

	public Proyecto() {
		super();
	}

}
