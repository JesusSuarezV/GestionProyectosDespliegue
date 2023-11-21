package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.Maquinaria;
import com.app.web.repositorio.MaquinariaRepositorio;

import java.util.List;

@Service
public class MaquinariaServicio {

	@Autowired
    private MaquinariaRepositorio repositorio;

    

    public List<Maquinaria> listarTodasLasMaquinarias() {
        return repositorio.findByVisibilidadTrue();
        
        
    }
    
    public Maquinaria guardarMaquinaria(Maquinaria maquinaria) {
    	
    	return repositorio.save(maquinaria);
    	
    }
    

    public Maquinaria obtenerMaquinariaPorId(int id) {
    	return repositorio.findById(id).get();
		
	}


    public Maquinaria crearMaquinaria(Maquinaria maquinaria) {
        return repositorio.save(maquinaria);
    }

    public Maquinaria actualizarMaquinaria(Maquinaria maquinaria) {
        return repositorio.save(maquinaria);
    }

    public void ocultarMaquinaria(int id) {
    	repositorio.softDelete(id);
    }
    
    public int obtenerMaximoId() {
    	
    	return repositorio.obtenerMaximoId();
    	
    }
    
    
    
}

