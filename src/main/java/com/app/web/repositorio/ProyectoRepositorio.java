package com.app.web.repositorio;

import java.awt.Image;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Proyecto;

@Repository //Etiqueta que indica el repositorio
public interface ProyectoRepositorio extends JpaRepository<Proyecto, Integer> {
	
	List<Proyecto> findByVisibilidadTrue(); //metodo para listar los proyectos visibles

	default void softDelete(int id) { //metodo para ocultar los proyectos
		Proyecto proyecto = findById(id).orElse(null);
		proyecto.setVisibilidad(false);
		save(proyecto);
	}
	
	@Query("SELECT COALESCE(MAX(p.id), 0) FROM Proyecto p") //consulta para obtener el valor de id maximo
    int obtenerMaximoId();

}
