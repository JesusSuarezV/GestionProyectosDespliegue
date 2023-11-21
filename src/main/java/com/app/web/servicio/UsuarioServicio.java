package com.app.web.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.app.web.dto.UsuarioRegistroDTO;
import com.app.web.entidad.Rol;
import com.app.web.entidad.Usuario;
import com.app.web.repositorio.UsuarioRepositorio;


public interface UsuarioServicio extends UserDetailsService{
	

	public Usuario guardarUsuario(UsuarioRegistroDTO registroDTO);
	public List<Usuario> listarUsuarios();
	public void ocultarUsuario(String correo);
	
}
