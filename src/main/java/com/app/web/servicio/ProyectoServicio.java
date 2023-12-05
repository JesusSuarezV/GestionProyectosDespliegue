package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.repositorio.ProyectoRepositorio;

import java.util.List;

@Service
public class ProyectoServicio {

	@Autowired
    private ProyectoRepositorio repositorio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
    

    public List<Proyecto> listarTodosLosProyectos() {
        return repositorio.findByVisibilidadTrue();
        
        
    }
    
    public Proyecto guardarProyecto(Proyecto proyecto) {
    	
    	return repositorio.save(proyecto);
    	
    }
    

    public Proyecto obtenerProyectoPorId(int id) {
    	return repositorio.findById(id).get();
		
	}


    public Proyecto crearProyecto(Proyecto proyecto) {
        return repositorio.save(proyecto);
    }

    public Proyecto actualizarProyecto(Proyecto proyecto) {
        return repositorio.save(proyecto);
    }

    public void ocultarProyecto(int id) {
    	repositorio.softDelete(id);
    }
    
    public int obtenerMaximoId() {
    	
    	return repositorio.obtenerMaximoId();
    	
    }
    
	public double obtenerValorDeUnProyecto(Proyecto proyecto) {
		double valor = 0;
		List<Subproyecto> subproyectos = subproyectoServicio.listarTodosLosSubproyectosDeUnProyecto(proyecto);
		for (Subproyecto subproyecto : subproyectos) {
			valor += subproyectoServicio.obtenerValorDeUnSubproyecto(subproyecto);

		}

		valor = Math.round(valor * 100d)/100d;
		
		return valor;

	}
    
    
}

