package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.Material;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Transporte;
import com.app.web.repositorio.APUItemSubproyectoRepositorio;
import com.app.web.repositorio.MaterialAPUItemSubproyectoRepositorio;
import com.app.web.repositorio.TransporteRepositorio;

@Service
public class TransporteServicio {

	@Autowired
	private TransporteRepositorio repositorio;

	public List<Transporte> listarTodosLoTransportesDeUnAPU(APUItemSubproyecto apuItemSubproyecto) {
		return repositorio.findByAPUItemSubproyectoAndVisibilidadTrue(apuItemSubproyecto);

	}

	public Transporte obtenerTransportePorId(int id) {
		return repositorio.findById(id).get();

	}

	public Transporte asignarTransporte(Transporte Transporte) {
		return repositorio.save(Transporte);
	}
	
	public Transporte actualizarTransporte(Transporte Transporte) {
		return repositorio.save(Transporte);
	}

	public void ocultarTransporte(int id) {
		repositorio.softDelete(id);
	}

	public int obtenerMaximoId() {

		return repositorio.obtenerMaximoId();

	}
	
	public double obtenerValorDeTansporteDeUnAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto) {
		 double valor = 0;
		 List<Transporte> transportes = listarTodosLoTransportesDeUnAPU(apuItemSubproyecto);
		 for(Transporte transporte : transportes){
			 valor += transporte.getValorParcial();
		 }
		 
		 valor = Math.round(valor * 100d)/100d;
		 
		return valor;
		 
	 }

}
