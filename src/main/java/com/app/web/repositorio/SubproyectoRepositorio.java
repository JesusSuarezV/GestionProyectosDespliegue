package com.app.web.repositorio;

import java.awt.Image;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;

@Repository //Etiqueta que indica el repositorio
public interface SubproyectoRepositorio extends JpaRepository<Subproyecto, Integer> {
	
	List<Subproyecto> findByProyectoAndVisibilidadTrue(Proyecto proyecto); //metodo para listar los subproyectos visibles

	default void softDelete(int id) { //metodo para ocultar los subproyectos
		Subproyecto subproyecto = findById(id).orElse(null);
		subproyecto.setVisibilidad(false);
		save(subproyecto);
	}
	
	@Query("SELECT COALESCE(MAX(s.id), 0) FROM Subproyecto s") //consulta para obtener el valor de id maximo
    int obtenerMaximoId();

}
