package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.web.servicio.APUItemSubproyectoServicio;
import com.app.web.servicio.APUServicio;
import com.app.web.servicio.ItemServicio;
import com.app.web.servicio.ItemSubproyectoServicio;
import com.app.web.servicio.ManoObraAPUItemSubproyectoServicio;
import com.app.web.servicio.MaquinariaAPUItemSubproyectoServicio;
import com.app.web.servicio.MaterialAPUItemSubproyectoServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.servicio.TransporteServicio;
import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.APU;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Transporte;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class APUItemSubproyectoControlador {

	@Autowired
	private APUItemSubproyectoServicio servicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private ProyectoServicio proyectoServicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
	@Autowired
	private APUServicio apuServicio;
	@Autowired
	private MaterialAPUItemSubproyectoServicio materialAPUItemSubproyectoServicio;
	@Autowired
	private TransporteServicio transporteServicio;
	@Autowired
	private MaquinariaAPUItemSubproyectoServicio maquinariaAPUItemSubproyectoServicio;
	@Autowired
	private ManoObraAPUItemSubproyectoServicio manoObraAPUItemSubproyectoServicio;
	
	@GetMapping({ "/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemId}/APU" }) 
	public String listarItemsDeUnSubproyecto(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemId ,Model model) {
		
	
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemId);
		List<APUItemSubproyecto> apu = servicio.listarTodosLosAPUDeUnItemDeUnSubproyecto(itemSubproyecto);
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apu", apu);
		return "APUItemSubproyecto/Ver_APU"; //plantilla que carga el metodo
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/Asignar_APU")//URL
	public String mostrarFormularioDeAsignarAPU(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId ,Model model) {
		
		
		List<APU> apu = apuServicio.obtenerAPUNoAsignados(itemSubproyectoId);
		
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = new APUItemSubproyecto();
		
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("apu", apu);
		return "APUItemSubproyecto/Asignar_APU";
	}
	
	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/Guardar")
	public String guardarAPUItemSubproyecto(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @RequestParam("apuId") int APUId, @RequestParam("cantidad") double cantidad){

		int id = servicio.obtenerMaximoId() + 1;
		APUItemSubproyecto apuItemSubproyecto = new APUItemSubproyecto(id, itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId), apuServicio.obtenerAPUPorId(APUId), cantidad, true);
		servicio.asignarAPU(apuItemSubproyecto);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, Model model) {

		model.addAttribute("proyecto", proyectoServicio.obtenerProyectoPorId(proyectoId)); 
		model.addAttribute("subproyecto", subproyectoServicio.obtenerSubproyectoPorId(subproyectoId));
		model.addAttribute("itemSubproyecto", itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId));
		model.addAttribute("apu", servicio.obtenerAPUItemSubproyectoPorId(id));
		return "APUItemSubproyecto/Editar_APU";

	}
	
	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{id}/Actualizar")
	public String actualizarAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @RequestParam("cantidad") double cantidad){

		APUItemSubproyecto apuItemSubproyectoExistente = servicio.obtenerAPUItemSubproyectoPorId(id);
		apuItemSubproyectoExistente.setCantidad(cantidad);
		servicio.actualizarAPU(apuItemSubproyectoExistente);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU"; //URL en donde retorna el metodo
		
	}
	
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{id}/Ocultar")
	public String ocultarAPUItemSubproyecto(@PathVariable int id) {

		servicio.ocultarAPUItemSubproyecto(id); //Se oculta

		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU"; //URL en donde retorna el metodo

	}
	
	
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{id}/Recursos")//URL
	public String mostrarInterfazDeRecursos(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int id ,Model model) {
		
		APUItemSubproyecto apuItemSubproyecto = servicio.obtenerAPUItemSubproyectoPorId(id);
		
		double totalMaterial = materialAPUItemSubproyectoServicio.obtenerValorDeMaterialesDeUnAPUItemSubproyecto(apuItemSubproyecto);
		
		double totalTransporte = transporteServicio.obtenerValorDeTansporteDeUnAPUItemSubproyecto(apuItemSubproyecto);
		
		double totalMaquinaria = maquinariaAPUItemSubproyectoServicio.obtenerValorDeMaquinariasDeUnAPUItemSubproyecto(apuItemSubproyecto);
		
		double totalManoObra = manoObraAPUItemSubproyectoServicio.obtenerValorDeManoDeObrasDeUnAPUItemSubproyecto(apuItemSubproyecto);
		
		model.addAttribute("proyecto", proyectoServicio.obtenerProyectoPorId(proyectoId)); 
		model.addAttribute("subproyecto", subproyectoServicio.obtenerSubproyectoPorId(subproyectoId));
		model.addAttribute("itemSubproyecto", itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId));
		model.addAttribute("apuItemSubproyecto", servicio.obtenerAPUItemSubproyectoPorId(id));
		model.addAttribute("totalMaterial", totalMaterial); 
		model.addAttribute("totalTransporte", totalTransporte); 
		model.addAttribute("totalMaquinaria", totalMaquinaria); 
		model.addAttribute("totalManoObra", totalManoObra); 
		
		return "Recursos/Recursos";
	}

}