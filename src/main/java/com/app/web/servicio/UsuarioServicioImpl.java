package com.app.web.servicio;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.web.dto.UsuarioRegistroDTO;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Rol;
import com.app.web.entidad.Usuario;
import com.app.web.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {
	@Autowired
	private UsuarioRepositorio repositorio;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repositorio.findByCorreoAndVisibilidadTrue(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario o Contraseña invalidos");
		}

		if (!usuario.isVisibilidad()) {
			throw new UsernameNotFoundException("Este Usuario anteriormente fue eliminado del sistema");
		}
		return new User(usuario.getCorreo(), usuario.getContraseña(), mapearAutoridadesRoles(usuario.getRol()));
	}

	@Override
	public Usuario guardarUsuario(UsuarioRegistroDTO registroDTO) {

		Usuario usuario = new Usuario(registroDTO.getCorreo(), registroDTO.getNombre(), registroDTO.getApellido(),
				registroDTO.getContraseña(), true, Arrays.asList(new Rol(1, "EDIT")));

		return repositorio.save(usuario);

	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
	}

	@Override
	public List<Usuario> listarUsuarios() {
		Rol rol = new Rol(1, "EDIT");
		return repositorio.findByRolAndVisibilidadTrue(rol);
	}

	@Override
	public void ocultarUsuario(String correo) {

		repositorio.softDelete(correo);
	}

	public boolean correoExistenteYVisible(String correo) {

		return repositorio.existsByCorreoAndVisibilidadTrue(correo);
	}
	
	 public Usuario obtenerUsuarioPorCorreo(String correo) {
	    	return repositorio.findByCorreo(correo);
			
		}

}
