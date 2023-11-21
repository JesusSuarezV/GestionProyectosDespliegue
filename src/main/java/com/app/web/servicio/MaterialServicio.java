package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.Material;
import com.app.web.repositorio.MaterialRepositorio;

import java.util.List;

@Service
public class MaterialServicio {

	@Autowired
    private MaterialRepositorio repositorio;

    

    public List<Material> listarTodosLosMateriales() {
        return repositorio.findByVisibilidadTrue();
        
        
    }
    
    public Material guardarMaterial(Material material) {
    	
    	return repositorio.save(material);
    	
    }
    

    public Material obtenerMaterialPorId(int id) {
    	return repositorio.findById(id).get();
		
	}


    public Material crearMaterial(Material material) {
        return repositorio.save(material);
    }

    public Material actualizarMaterial(Material material) {
        return repositorio.save(material);
    }

    public void ocultarMaterial(int id) {
    	repositorio.softDelete(id);
    }
    
    public int obtenerMaximoId() {
    	
    	return repositorio.obtenerMaximoId();
    	
    }
    
    
    
}

