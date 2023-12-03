package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;

@Repository
public interface MaquinariaAPUItemSubproyectoRepositorio extends JpaRepository<MaquinariaAPUItemSubproyecto, Integer> {
	
	List<MaquinariaAPUItemSubproyecto> findByapuItemSubproyectoAndVisibilidadTrue(APUItemSubproyecto apuItemSubproyecto);
	
	default void softDelete(int id) { 
		MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto = findById(id).orElse(null);
		maquinariaAPUItemSubproyecto.setVisibilidad(false);
		save(maquinariaAPUItemSubproyecto);
	}
	
	@Query("SELECT COALESCE(MAX(m.id), 0) FROM MaquinariaAPUItemSubproyecto m")
    int obtenerMaximoId();

}
