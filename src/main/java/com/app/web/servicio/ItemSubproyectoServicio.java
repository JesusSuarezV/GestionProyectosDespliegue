package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Transporte;
import com.app.web.repositorio.ItemSubproyectoRepositorio;

@Service
public class ItemSubproyectoServicio {

	@Autowired
	private ItemSubproyectoRepositorio repositorio;
	
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;

	public List<ItemSubproyecto> listarTodosLosItemsDeUnSubproyecto(Subproyecto subproyecto) {
		return repositorio.findBySubproyectoAndVisibilidadTrue(subproyecto);

	}

	public ItemSubproyecto obtenerItemSubproyectoPorId(int id) {
		return repositorio.findById(id).get();

	}

	public ItemSubproyecto asignarItem(ItemSubproyecto itemSubproyecto) {
		return repositorio.save(itemSubproyecto);
	}

	public void ocultarItemSubproyecto(int id) {
		repositorio.softDelete(id);
	}

	public int obtenerMaximoId() {

		return repositorio.obtenerMaximoId();

	}
	
	public double obtenerValorDeUnItemSubproyecto(ItemSubproyecto itemSubproyecto) {

		double valor = 0;
		List<APUItemSubproyecto> apuSubproyetos = apuItemSubproyectoServicio
				.listarTodosLosAPUDeUnItemDeUnSubproyecto(itemSubproyecto);
		for (APUItemSubproyecto apuItemSubproyecto : apuSubproyetos) {

			valor += apuItemSubproyectoServicio.obtenerValorDeUnAPUItemSubproyecto(apuItemSubproyecto);
		}

		valor = Math.round(valor * 100d)/100d;
		
		return valor;

	}
	
	

}
