package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.ManoObra;
import com.app.web.repositorio.ManoObraRepositorio;

import java.util.List;

@Service
public class ManoObraServicio {

	@Autowired
    private ManoObraRepositorio repositorio;

    

    public List<ManoObra> listarTodosLasManoObras() {
        return repositorio.findByVisibilidadTrue();
        
        
    }
    
    public ManoObra guardarManoObra(ManoObra manoObra) {
    	
    	return repositorio.save(manoObra);
    	
    }
    

    public ManoObra obtenerManoObrasPorId(int id) {
    	return repositorio.findById(id).get();
		
	}


    public ManoObra crearManoObra(ManoObra manoObra) {
        return repositorio.save(manoObra);
    }

    public ManoObra actualizarManoObra(ManoObra manoObra) {
        return repositorio.save(manoObra);
    }

    public void ocultarManoObra(int id) {
    	repositorio.softDelete(id);
    }
    
    public int obtenerMaximoId() {
    	
    	return repositorio.obtenerMaximoId();
    	
    }
    
    
    
}

