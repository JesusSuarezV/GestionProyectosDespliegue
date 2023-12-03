package com.app.web.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.APU;
import com.app.web.entidad.Item;

@Repository //Etiqueta que indica el repositorio
public interface APURepositorio extends JpaRepository<APU, Integer> {
	
	List<APU> findByVisibilidadTrue(); //metodo para listar los APU visibles

	default void softDelete(int id) { //metodo para ocultar los APU
		APU apu = findById(id).orElse(null);
		apu.setVisibilidad(false);
		save(apu);
	}
	
	@Query("SELECT COALESCE(MAX(a.id), 0) FROM APU a") //consulta para obtener el valor de id maximo
    int obtenerMaximoId();

	boolean existsByNombreAndVisibilidadTrue(String nombre);
	
	@Query("SELECT a FROM APU a WHERE a NOT IN (SELECT i.apu FROM APUItemSubproyecto i WHERE i.itemSubproyecto.id = :itemSubproyectoId AND i.visibilidad = true) AND a.visibilidad = true")
    List<APU> findAPUNotInItemSubproyecto(@Param("itemSubproyectoId") int itemSubproyectoId);

}
