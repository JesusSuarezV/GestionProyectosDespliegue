package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.Material;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.repositorio.APUItemSubproyectoRepositorio;
import com.app.web.repositorio.MaterialAPUItemSubproyectoRepositorio;

@Service
public class MaterialAPUItemSubproyectoServicio {

	@Autowired
	private MaterialAPUItemSubproyectoRepositorio repositorio;

	public List<MaterialAPUItemSubproyecto> listarTodosLosMaterialesDeUnAPU(APUItemSubproyecto apuItemSubproyecto) {
		return repositorio.findByapuItemSubproyectoAndVisibilidadTrue(apuItemSubproyecto);

	}

	public MaterialAPUItemSubproyecto obtenerMaterialAPUItemSubproyectoPorId(int id) {
		return repositorio.findById(id).get();

	}

	public MaterialAPUItemSubproyecto asignarMaterial(MaterialAPUItemSubproyecto materialAPUItemSubproyecto) {
		return repositorio.save(materialAPUItemSubproyecto);
	}
	
	public MaterialAPUItemSubproyecto actualizarMaterialAsignado(MaterialAPUItemSubproyecto materialAPUItemSubproyecto) {
		return repositorio.save(materialAPUItemSubproyecto);
	}

	public void ocultarMaterialAPUItemSubproyecto(int id) {
		repositorio.softDelete(id);
	}

	public int obtenerMaximoId() {

		return repositorio.obtenerMaximoId();

	}
	
	 public List<MaterialAPUItemSubproyecto> obtenerMaterialAsignadoNoTransportado(int APUitemSubproyectoId) {
	    	return repositorio.findMaterialesAsignadosNotInTransporte(APUitemSubproyectoId);
	    }
	 
	 public double obtenerValorDeMaterialesDeUnAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto) {
		 double valor = 0;
		 List<MaterialAPUItemSubproyecto> materialAPUItemSubproyectos = listarTodosLosMaterialesDeUnAPU(apuItemSubproyecto);
		 for(MaterialAPUItemSubproyecto materialAPUItemSubproyecto : materialAPUItemSubproyectos){
			 valor += materialAPUItemSubproyecto.getValorParcial();
		 }
		 
		 valor = Math.round(valor * 100d)/100d;
		 
		return valor;
		 
	 }

}
