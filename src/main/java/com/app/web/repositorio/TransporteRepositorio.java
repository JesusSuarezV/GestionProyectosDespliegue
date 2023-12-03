package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.Material;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Transporte;

@Repository
public interface TransporteRepositorio extends JpaRepository<Transporte, Integer> {
	
	
	@Query("SELECT t FROM Transporte t WHERE t.materialAPUItemSubproyecto.apuItemSubproyecto = :apuItemSubproyecto AND t.visibilidad = true")
	    List<Transporte> findByAPUItemSubproyectoAndVisibilidadTrue(
	            @Param("apuItemSubproyecto") APUItemSubproyecto apuItemSubproyectoId);
	
	
	default void softDelete(int id) { 
		Transporte transporte = findById(id).orElse(null);
		transporte.setVisibilidad(false);
		save(transporte);
	}
	
	@Query("SELECT COALESCE(MAX(t.id), 0) FROM Transporte t")
    int obtenerMaximoId();

	
}
