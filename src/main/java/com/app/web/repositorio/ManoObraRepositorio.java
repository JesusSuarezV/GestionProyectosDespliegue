package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.ManoObra;
import com.app.web.entidad.Material;

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
	
	@Query("SELECT m FROM ManoObra m WHERE m NOT IN (SELECT a.manoObra FROM ManoObraAPUItemSubproyecto a WHERE a.apuItemSubproyecto.id = :apuItemSubproyectoId AND a.visibilidad = true) AND m.visibilidad = true")
    List<ManoObra> findManoObrasNotInAPUItemSubproyecto(@Param("apuItemSubproyectoId") int apuItemSubproyectoId);

}
