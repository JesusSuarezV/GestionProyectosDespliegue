package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //Etiqueta de Entidad
@Table(name = "item") //Etiqueta que relaciona la tabla de la base de datos con su nombre"
public class Item {
	@Id//Etiquet de llave primaria
	@Column(name = "Id")//Etiqueta de la columna con su nombre
	int id;
	@Column(name = "Nombre")
	String nombre;
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
	public boolean isVisibilidad() {
		return visibilidad;
	}
	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}
	//Constructores
	public Item(int id, String nombre, boolean visibilidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.visibilidad = visibilidad;
	}
	public Item() {
		super();
	}
	
	
	
	
	
}
