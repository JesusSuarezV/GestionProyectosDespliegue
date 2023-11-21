package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.APU;
import com.app.web.repositorio.APURepositorio;

import java.util.List;

@Service
public class APUServicio {

	@Autowired
    private APURepositorio repositorio;

    

    public List<APU> listarTodosLosAPU() {
        return repositorio.findByVisibilidadTrue();
        
        
    }
    
    public APU guardarAPU(APU apu) {
    	
    	return repositorio.save(apu);
    	
    }
    

    public APU obtenerAPUPorId(int id) {
    	return repositorio.findById(id).get();
		
	}


    public APU crearAPU(APU apu) {
        return repositorio.save(apu);
    }

    public APU actualizarAPU(APU apu) {
        return repositorio.save(apu);
    }

    public void ocultarAPU(int id) {
    	repositorio.softDelete(id);
    }
    
    public int obtenerMaximoId() {
    	
    	return repositorio.obtenerMaximoId();
    	
    }
    
    
    
}

