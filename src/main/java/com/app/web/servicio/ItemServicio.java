package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.entidad.Item;
import com.app.web.repositorio.ItemRepositorio;

import java.util.List;

@Service
public class ItemServicio {

	@Autowired
    private ItemRepositorio repositorio;

    

    public List<Item> listarTodosLosItems() {
        return repositorio.findByVisibilidadTrue();
        
        
    }
    
    public Item guardarItem(Item item) {
    	
    	return repositorio.save(item);
    	
    }
    

    public Item obtenerItemPorId(int id) {
    	return repositorio.findById(id).get();
		
	}


    public Item crearItem(Item item) {
        return repositorio.save(item);
    }

    public Item actualizarItem(Item item) {
        return repositorio.save(item);
    }

    public void ocultarItem(int id) {
    	repositorio.softDelete(id);
    }
    
    public int obtenerMaximoId() {
    	
    	return repositorio.obtenerMaximoId();
    	
    }
    
    
    
}

