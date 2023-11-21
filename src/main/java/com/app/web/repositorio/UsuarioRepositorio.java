package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Rol;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

	public Usuario findByCorreoAndVisibilidadTrue(String correo);
	public Usuario findByCorreoAndVisibilidadFalse(String correo);


	default void softDelete(String correo) { // metodo para ocultar los usuarios
		Usuario usuario = findByCorreoAndVisibilidadTrue(correo);
		usuario.setVisibilidad(false);
		save(usuario);
	}

	public List<Usuario> findByRolAndVisibilidadTrue(Rol rol);


	public boolean existsByCorreoAndVisibilidadTrue(String correo);
	public Usuario findByCorreo(String correo);
}
