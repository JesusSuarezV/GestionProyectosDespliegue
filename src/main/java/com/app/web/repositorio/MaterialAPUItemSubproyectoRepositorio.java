package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.Material;
import com.app.web.entidad.MaterialAPUItemSubproyecto;

@Repository
public interface MaterialAPUItemSubproyectoRepositorio extends JpaRepository<MaterialAPUItemSubproyecto, Integer> {
	
	List<MaterialAPUItemSubproyecto> findByapuItemSubproyectoAndVisibilidadTrue(APUItemSubproyecto apuItemSubproyecto);
	
	default void softDelete(int id) { 
		MaterialAPUItemSubproyecto materialAPUItemSubproyecto = findById(id).orElse(null);
		materialAPUItemSubproyecto.setVisibilidad(false);
		save(materialAPUItemSubproyecto);
	}
	
	@Query("SELECT COALESCE(MAX(m.id), 0) FROM MaterialAPUItemSubproyecto m")
    int obtenerMaximoId();

	@Query("SELECT m FROM MaterialAPUItemSubproyecto m WHERE m NOT IN (SELECT t.materialAPUItemSubproyecto FROM Transporte t WHERE t.materialAPUItemSubproyecto.apuItemSubproyecto.id = :apuItemSubproyectoId AND t.visibilidad = true) AND m.apuItemSubproyecto.id = :apuItemSubproyectoId AND m.visibilidad = true")
    List<MaterialAPUItemSubproyecto> findMaterialesAsignadosNotInTransporte(@Param("apuItemSubproyectoId") int apuItemSubproyectoId);
	
}
