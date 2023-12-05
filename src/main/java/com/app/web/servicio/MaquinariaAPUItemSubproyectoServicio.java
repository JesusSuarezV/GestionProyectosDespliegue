package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.repositorio.APUItemSubproyectoRepositorio;
import com.app.web.repositorio.MaquinariaAPUItemSubproyectoRepositorio;
import com.app.web.repositorio.MaterialAPUItemSubproyectoRepositorio;

@Service
public class MaquinariaAPUItemSubproyectoServicio {

	@Autowired
	private MaquinariaAPUItemSubproyectoRepositorio repositorio;

	public List<MaquinariaAPUItemSubproyecto> listarTodasLasMaquinariasDeUnAPU(APUItemSubproyecto apuItemSubproyecto) {
		return repositorio.findByapuItemSubproyectoAndVisibilidadTrue(apuItemSubproyecto);

	}

	public MaquinariaAPUItemSubproyecto obtenerMaquinariaAPUItemSubproyectoPorId(int id) {
		return repositorio.findById(id).get();

	}

	public MaquinariaAPUItemSubproyecto asignarMaquinaria(MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto) {
		return repositorio.save(maquinariaAPUItemSubproyecto);
	}
	
	public MaquinariaAPUItemSubproyecto actualizarMaquinariaAsignada(MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto) {
		return repositorio.save(maquinariaAPUItemSubproyecto);
	}

	public void ocultarMaquinariaAPUItemSubproyecto(int id) {
		repositorio.softDelete(id);
	}

	public int obtenerMaximoId() {

		return repositorio.obtenerMaximoId();

	}
	
	public double obtenerValorDeMaquinariasDeUnAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto) {
		 double valor = 0;
		 List<MaquinariaAPUItemSubproyecto> maquinariaAPUItemSubproyectos = listarTodasLasMaquinariasDeUnAPU(apuItemSubproyecto);
		 for(MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto : maquinariaAPUItemSubproyectos){
			 valor += maquinariaAPUItemSubproyecto.getValorParcial();
		 }
		 
		 valor = Math.round(valor * 100d)/100d;
		 
		return valor;
		 
	 }

}
