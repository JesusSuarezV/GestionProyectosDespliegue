package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Maquinaria;

@Repository //Etiqueta que indica el repositorio
public interface MaquinariaRepositorio extends JpaRepository<Maquinaria, Integer> {
	
	List<Maquinaria> findByVisibilidadTrue(); //metodo para listar las Maquinaria visibles

	default void softDelete(int id) { //metodo para ocultar las Maquinaria
		Maquinaria maquinaria = findById(id).orElse(null);
		maquinaria.setVisibilidad(false);
		save(maquinaria);
	}
	
	@Query("SELECT COALESCE(MAX(m.id), 0) FROM Maquinaria m") //consulta para obtener el valor de id maximo
    int obtenerMaximoId();

}
