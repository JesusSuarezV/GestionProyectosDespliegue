package com.app.web.dto;

public class UsuarioRegistroDTO {

	private String correo;
	private String nombre;
	private String apellido;
	private String contraseña;
	private boolean visibilidad;

	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
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

	public UsuarioRegistroDTO(String nombre, String apellido, String correo, String contraseña,
			boolean visibilidad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.contraseña = contraseña;
		this.visibilidad = visibilidad;
	}

	public UsuarioRegistroDTO(String correo) {
		super();
		this.correo = correo;
	}

	public UsuarioRegistroDTO() {
		super();
	}

}
