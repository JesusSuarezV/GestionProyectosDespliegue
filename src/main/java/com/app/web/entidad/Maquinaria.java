package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //Etiqueta de Entidad
@Table(name = "maquinaria") //Etiqueta que relaciona la tabla de la base de datos con su nombre"
public class Maquinaria {
	@Id//Etiquet de llave primaria
	@Column(name = "Id")//Etiqueta de la columna con su nombre
	int id;
	@Column(name = "Nombre")
	String nombre;
	@Column(name = "Unidad")
	String unidad;
	@Column(name = "Visibilidad")
	boolean visibilidad;
	//Getter And Setters
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
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public boolean isVisibilidad() {
		return visibilidad;
	}
	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}
	public Maquinaria(int id, String nombre, String unidad, boolean visibilidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.unidad = unidad;
		this.visibilidad = visibilidad;
	}
	public Maquinaria() {
		super();
	}
	
}