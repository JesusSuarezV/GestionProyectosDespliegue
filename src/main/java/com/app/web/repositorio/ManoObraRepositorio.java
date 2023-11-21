package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.ManoObra;

@Repository //Etiqueta que indica el repositorio
public interface ManoObraRepositorio extends JpaRepository<ManoObra, Integer> {
	
	List<ManoObra> findByVisibilidadTrue(); //metodo para listar las Mano de Obra visibles

	default void softDelete(int id) { //metodo para ocultar las Mano de Obra
		ManoObra manoObra = findById(id).orElse(null);
		manoObra.setVisibilidad(false);
		save(manoObra);
	}
	
	@Query("SELECT COALESCE(MAX(m.id), 0) FROM ManoObra m") //consulta para obtener el valor de id maximo
    int obtenerMaximoId();

}
