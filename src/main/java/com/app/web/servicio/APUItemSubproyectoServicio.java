package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.repositorio.APUItemSubproyectoRepositorio;

@Service
public class APUItemSubproyectoServicio {

	@Autowired
	private APUItemSubproyectoRepositorio repositorio;

	public List<APUItemSubproyecto> listarTodosLosAPUDeUnItemDeUnSubproyecto(ItemSubproyecto itemSubproyecto) {
		return repositorio.findByItemSubproyectoAndVisibilidadTrue(itemSubproyecto);

	}

	public APUItemSubproyecto obtenerAPUItemSubproyectoPorId(int id) {
		return repositorio.findById(id).get();

	}

	public APUItemSubproyecto asignarAPU(APUItemSubproyecto apuItemSubproyecto) {
		return repositorio.save(apuItemSubproyecto);
	}
	
	public APUItemSubproyecto actualizarAPU(APUItemSubproyecto apuItemSubproyecto) {
		return repositorio.save(apuItemSubproyecto);
	}

	public void ocultarAPUItemSubproyecto(int id) {
		repositorio.softDelete(id);
	}

	public int obtenerMaximoId() {

		return repositorio.obtenerMaximoId();

	}

}
