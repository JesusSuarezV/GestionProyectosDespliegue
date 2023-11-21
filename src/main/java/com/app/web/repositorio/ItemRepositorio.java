package com.app.web.repositorio;

import java.awt.Image;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Item;

@Repository //Etiqueta que indica el repositorio
public interface ItemRepositorio extends JpaRepository<Item, Integer> {
	
	List<Item> findByVisibilidadTrue(); //metodo para listar los items visibles

	default void softDelete(int id) { //metodo para ocultar los items
		Item item = findById(id).orElse(null);
		item.setVisibilidad(false);
		save(item);
	}
	
	@Query("SELECT COALESCE(MAX(i.id), 0) FROM Item i") //consulta para obtener el valor de id maximo
    int obtenerMaximoId();

}
