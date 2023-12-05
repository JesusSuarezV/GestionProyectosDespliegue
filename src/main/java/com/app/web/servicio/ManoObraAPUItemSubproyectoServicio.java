package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.repositorio.APUItemSubproyectoRepositorio;
import com.app.web.repositorio.ManoObraAPUItemSubproyectoRepositorio;
import com.app.web.repositorio.MaquinariaAPUItemSubproyectoRepositorio;
import com.app.web.repositorio.MaterialAPUItemSubproyectoRepositorio;

@Service
public class ManoObraAPUItemSubproyectoServicio {

	@Autowired
	private ManoObraAPUItemSubproyectoRepositorio repositorio;

	public List<ManoObraAPUItemSubproyecto> listarTodasLasManosDeObraDeUnAPU(APUItemSubproyecto apuItemSubproyecto) {
		return repositorio.findByapuItemSubproyectoAndVisibilidadTrue(apuItemSubproyecto);

	}

	public ManoObraAPUItemSubproyecto obtenerManoObraAPUItemSubproyectoPorId(int id) {
		return repositorio.findById(id).get();

	}

	public ManoObraAPUItemSubproyecto asignarManoDeObra(ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto) {
		return repositorio.save(manoObraAPUItemSubproyecto);
	}
	
	public ManoObraAPUItemSubproyecto actualizarManoDeObraAsignada(ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto) {
		return repositorio.save(manoObraAPUItemSubproyecto);
	}

	public void ocultarManoObraAPUItemSubproyecto(int id) {
		repositorio.softDelete(id);
	}

	public int obtenerMaximoId() {

		return repositorio.obtenerMaximoId();

	}
	
	public double obtenerValorDeManoDeObrasDeUnAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto) {
		 double valor = 0;
		 List<ManoObraAPUItemSubproyecto> manoObraAPUItemSubproyectos = listarTodasLasManosDeObraDeUnAPU(apuItemSubproyecto);
		 for(ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto : manoObraAPUItemSubproyectos){
			 valor += manoObraAPUItemSubproyecto.getValorParcial();
		 }
		 
		 valor = Math.round(valor * 100d)/100d;
		 
		return valor;
		 
	 }

}
