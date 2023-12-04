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
	@Autowired
	private MaterialAPUItemSubproyectoServicio materialAPUItemSubproyectoServicio;
	@Autowired
	private TransporteServicio transporteServicio;
	@Autowired
	private MaquinariaAPUItemSubproyectoServicio maquinariaAPUItemSubproyectoServicio;
	@Autowired
	private ManoObraAPUItemSubproyectoServicio manoObraAPUItemSubproyectoServicio;

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
	
	public double obtenerValorDelItemSubproyecto(ItemSubproyecto itemSubproyecto) {
		
		double valorItemSubproyecto = 0;
		double valorAPU = 0;
		List<APUItemSubproyecto>apuSubproyetos = apuItemSubproyectoServicio.listarTodosLosAPUDeUnItemDeUnSubproyecto(itemSubproyecto);
		for(APUItemSubproyecto apuItemSubproyecto : apuSubproyetos) {
			valorAPU = 0;
			List<MaterialAPUItemSubproyecto> materiales = materialAPUItemSubproyectoServicio.listarTodosLosMaterialesDeUnAPU(apuItemSubproyecto);
			valorAPU += materiales.stream().mapToDouble(MaterialAPUItemSubproyecto::getValorParcial).sum();
			List<Transporte> transportes = transporteServicio.listarTodosLoTransportesDeUnAPU(apuItemSubproyecto);
			valorAPU += transportes.stream().mapToDouble(Transporte::getValorParcial).sum();
			List<MaquinariaAPUItemSubproyecto> maquinarias = maquinariaAPUItemSubproyectoServicio.listarTodasLasMaquinariasDeUnAPU(apuItemSubproyecto);
			valorAPU += maquinarias.stream().mapToDouble(MaquinariaAPUItemSubproyecto::getValorParcial).sum();
			List<ManoObraAPUItemSubproyecto> manoObras = manoObraAPUItemSubproyectoServicio.listarTodasLasManosDeObraDeUnAPU(apuItemSubproyecto);
			valorAPU += manoObras.stream().mapToDouble(ManoObraAPUItemSubproyecto::getValorParcial).sum();
			
			valorItemSubproyecto += valorAPU * apuItemSubproyecto.getCantidad();
			
		}
		
		return Math.round(valorItemSubproyecto * 100d)/100d;
		
		
	}
	
	

}
