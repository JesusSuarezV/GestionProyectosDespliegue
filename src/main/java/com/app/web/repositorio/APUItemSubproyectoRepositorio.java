package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;

@Repository
public interface APUItemSubproyectoRepositorio extends JpaRepository<APUItemSubproyecto, Integer> {
	
	List<APUItemSubproyecto> findByItemSubproyectoAndVisibilidadTrue(ItemSubproyecto itemSubproyecto);
	
	default void softDelete(int id) { 
		APUItemSubproyecto apuItemSubproyecto = findById(id).orElse(null);
		apuItemSubproyecto.setVisibilidad(false);
		save(apuItemSubproyecto);
	}
	
	@Query("SELECT COALESCE(MAX(a.id), 0) FROM APUItemSubproyecto a")
    int obtenerMaximoId();

}
