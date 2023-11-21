package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.repositorio.ProyectoRepositorio;
import com.app.web.repositorio.SubproyectoRepositorio;

import java.util.List;

@Service
public class SubproyectoServicio {

	@Autowired
    private SubproyectoRepositorio repositorio;

    

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
    
    
    
}

