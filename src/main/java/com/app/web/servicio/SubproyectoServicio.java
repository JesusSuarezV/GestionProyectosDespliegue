package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Transporte;
import com.app.web.repositorio.ProyectoRepositorio;
import com.app.web.repositorio.SubproyectoRepositorio;

import java.util.List;

@Service
public class SubproyectoServicio {

	@Autowired
    private SubproyectoRepositorio repositorio;
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private MaterialAPUItemSubproyectoServicio materialAPUItemSubproyectoServicio;
	@Autowired
	private TransporteServicio transporteServicio;
	@Autowired
	private MaquinariaAPUItemSubproyectoServicio maquinariaAPUItemSubproyectoServicio;
	@Autowired
	private ManoObraAPUItemSubproyectoServicio manoObraAPUItemSubproyectoServicio;

    

    public List<Subproyecto> listarTodosLosSubproyectosDeUnProyecto(Proyecto proyecto) {
        return repositorio.findByProyectoAndVisibilidadTrue(proyecto);
        
        
    }
    
    public Subproyecto guardarSubproyecto(Subproyecto subproyecto) {
    	
    	return repositorio.save(subproyecto);
    	
    }
    

    public Subproyecto obtenerSubproyectoPorId(int id) {
    	return repositorio.findById(id).get();
		
	}


    public Subproyecto crearSubproyecto(Subproyecto subproyecto) {
        return repositorio.save(subproyecto);
    }

    public Subproyecto actualizarSubproyecto(Subproyecto subproyecto) {
        return repositorio.save(subproyecto);
    }

    public void ocultarSubproyecto(int id) {
    	repositorio.softDelete(id);
    }
    
    public int obtenerMaximoId() {
    	
    	return repositorio.obtenerMaximoId();
    	
    }
    
	public double obtenerValorDeUnSubproyecto(Subproyecto subproyecto) {
		double valor = 0;
		List<ItemSubproyecto> itemSubproyectos = itemSubproyectoServicio
				.listarTodosLosItemsDeUnSubproyecto(subproyecto);
		for (ItemSubproyecto itemSubproyecto : itemSubproyectos) {
			valor += itemSubproyectoServicio.obtenerValorDeUnItemSubproyecto(itemSubproyecto);

		}

		valor = Math.round(valor * 100d)/100d;
		
		return valor;

	}
    
    
}

