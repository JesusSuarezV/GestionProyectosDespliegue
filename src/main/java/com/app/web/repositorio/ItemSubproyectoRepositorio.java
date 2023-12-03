package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.Subproyecto;

@Repository
public interface ItemSubproyectoRepositorio extends JpaRepository<ItemSubproyecto, Integer> {
	
	List<ItemSubproyecto> findBySubproyectoAndVisibilidadTrue(Subproyecto subproyecto);
	
	default void softDelete(int id) { 
		ItemSubproyecto itemSubproyecto = findById(id).orElse(null);
		itemSubproyecto.setVisibilidad(false);
		save(itemSubproyecto);
	}
	
	@Query("SELECT COALESCE(MAX(i.id), 0) FROM ItemSubproyecto i")
    int obtenerMaximoId();

}
