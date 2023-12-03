package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;

@Repository
public interface ManoObraAPUItemSubproyectoRepositorio extends JpaRepository<ManoObraAPUItemSubproyecto, Integer> {
	
	List<ManoObraAPUItemSubproyecto> findByapuItemSubproyectoAndVisibilidadTrue(APUItemSubproyecto apuItemSubproyecto);
	
	default void softDelete(int id) { 
		ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto = findById(id).orElse(null);
		manoObraAPUItemSubproyecto.setVisibilidad(false);
		save(manoObraAPUItemSubproyecto);
	}
	
	@Query("SELECT COALESCE(MAX(m.id), 0) FROM ManoObraAPUItemSubproyecto m")
    int obtenerMaximoId();

}
