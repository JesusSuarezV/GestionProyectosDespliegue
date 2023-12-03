package com.app.web.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.web.servicio.ItemSubproyectoServicio;
import com.app.web.servicio.SubproyectoServicio;

@Entity // Etiqueta de Entidad
@Table(name = "subproyecto") // Etiqueta que relaciona la tabla de la base de datos con su nombre"
public class Subproyecto {
	@Id // Etiquet de llave primaria
	@Column(name = "id") // Etiqueta de la columna con su nombre
	int id;
	@Column(name = "nombre")
	String nombre;
	@ManyToOne
	@JoinColumn(name = "proyecto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_proyecto"))
	Proyecto proyecto;
	@Column(name = "visibilidad")
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
	

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	// Constructores
	public Subproyecto(int id, String nombre, Proyecto proyecto, boolean visibilidad) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.visibilidad = visibilidad;
			this.proyecto = proyecto;
		}

	public Subproyecto() {
			super();
		}

}
