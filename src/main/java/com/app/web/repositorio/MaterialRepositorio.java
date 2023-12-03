package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Item;
import com.app.web.entidad.Material;

@Repository //Etiqueta que indica el repositorio
public interface MaterialRepositorio extends JpaRepository<Material, Integer> {
	
	List<Material> findByVisibilidadTrue(); //metodo para listar los Materiales visibles

	default void softDelete(int id) { //metodo para ocultar los Materiales
		Material material = findById(id).orElse(null);
		material.setVisibilidad(false);
		save(material);
	}
	
	@Query("SELECT COALESCE(MAX(m.id), 0) FROM Material m") //consulta para obtener el valor de id maximo
    int obtenerMaximoId();
	
	@Query("SELECT m FROM Material m WHERE m NOT IN (SELECT a.material FROM MaterialAPUItemSubproyecto a WHERE a.apuItemSubproyecto.id = :apuItemSubproyectoId AND a.visibilidad = true) AND m.visibilidad = true")
    List<Material> findMaterialesNotInAPUItemSubproyecto(@Param("apuItemSubproyectoId") int apuItemSubproyectoId);

}
