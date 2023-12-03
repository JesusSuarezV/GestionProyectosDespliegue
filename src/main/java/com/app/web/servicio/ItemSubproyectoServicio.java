package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.repositorio.ItemSubproyectoRepositorio;

@Service
public class ItemSubproyectoServicio {

	@Autowired
	private ItemSubproyectoRepositorio repositorio;

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
	
	

}
