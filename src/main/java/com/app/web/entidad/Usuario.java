package com.app.web.entidad;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "correo"))
public class Usuario {

	@Id
	@Column(name = "correo")
	private String correo;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "contraseña")
	private String contraseña;
	@Column(name = "visibilidad")
	private boolean visibilidad;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "usuario_rol",
			joinColumns = @JoinColumn(name = "usuario_correo", referencedColumnName = "correo"),
			inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private Collection<Rol> rol;


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public boolean isVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(boolean visibilidad) {
		this.visibilidad = visibilidad;
	}

	public Collection<Rol> getRol() {
		return rol;
	}

	public void setRol(Collection<Rol> rol) {
		this.rol = rol;
	}

	public Usuario(String correo, String nombre, String apellido, String contraseña, boolean visibilidad,
			Collection<com.app.web.entidad.Rol> rol) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.contraseña = contraseña;
		this.visibilidad = visibilidad;
		this.rol = rol;
	}

	public Usuario() {
		super();
	}

}
