package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //Etiqueta de Entidad
@Table(name = "material") //Etiqueta que relaciona la tabla de la base de datos con su nombre"
public class Material {
	@Id//Etiquet de llave primaria
	@Column(name = "Id")//Etiqueta de la columna con su nombre
	int id;
	@Column(name = "Nombre")
	String nombre;
	@Column(name = "Unidad_Metrica")
	String unidadMetrica;
	@Column(name = "Observacion")
	String observacion;
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
	public String getUnidadMetrica() {
		return unidadMetrica;
	}
	public void setUnidadMetrica(String unidadMetrica) {
		this.unidadMetrica = unidadMetrica;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public boolean isVisibilidad() {
		return visibilidad;
	}
	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}
	public Material(int id, String nombre, String unidadMetrica, String observacion, boolean visibilidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.unidadMetrica = unidadMetrica;
		this.observacion = observacion;
		this.visibilidad = visibilidad;
	}
	public Material() {
		super();
	}
	
}