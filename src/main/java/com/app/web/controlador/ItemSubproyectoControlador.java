package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.web.servicio.ItemServicio;
import com.app.web.servicio.ItemSubproyectoServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.entidad.Item;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ItemSubproyectoControlador {

	@Autowired
	private ItemSubproyectoServicio servicio;
	@Autowired
	private ProyectoServicio proyectoServicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
	@Autowired
	private ItemServicio itemServicio;
	
	@GetMapping({ "/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items" }) 
	public String listarItemsDeUnSubproyecto(@PathVariable int proyectoId, @PathVariable int subproyectoId, Model model) {
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		List<ItemSubproyecto> items = servicio.listarTodosLosItemsDeUnSubproyecto(subproyecto);
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("items", items);
		return "ItemSubproyecto/Ver_Items"; //plantilla que carga el metodo
	}

	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/Asignar_Item")//URL
	public String mostrarFormularioDeAsignarItems(@PathVariable int proyectoId, @PathVariable int subproyectoId, Model model) {
		List<Item> items = itemServicio.obtenerItemsNoAsignados(subproyectoId);
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = new ItemSubproyecto();
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("items", items);
		return "ItemSubproyecto/Asignar_Item";
	}

	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/Guardar")
	public String guardarItemSubproyecto(@PathVariable int proyectoId, @PathVariable int subproyectoId, @RequestParam("itemId") int itemId){

		int id = servicio.obtenerMaximoId() + 1;
		ItemSubproyecto itemSubproyecto = new ItemSubproyecto(id, itemServicio.obtenerItemPorId(itemId), subproyectoServicio.obtenerSubproyectoPorId(subproyectoId), true);
		servicio.asignarItem(itemSubproyecto);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{id}/Ocultar")
	public String ocultarItemSubproyecto(@PathVariable int id) {

		servicio.ocultarItemSubproyecto(id); //Se oculta

		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items"; //URL en donde retorna el metodo

	}

}