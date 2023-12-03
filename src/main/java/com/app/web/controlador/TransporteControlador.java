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
import com.app.web.servicio.MaterialAPUItemSubproyectoServicio;
import com.app.web.servicio.MaterialServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.servicio.TransporteServicio;
import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.APU;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.Material;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Transporte;

import javax.servlet.http.HttpServletResponse;

import java.awt.SystemColor;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class TransporteControlador {

	@Autowired
	private MaterialAPUItemSubproyectoServicio materialAPUItemSubproyectoServicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private ProyectoServicio proyectoServicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;
	@Autowired
	private TransporteServicio servicio;
	
	@GetMapping({ "/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes" }) 
	public String listarTransporteDeUnAPU(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
	
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		List<Transporte> transportes = servicio.listarTodosLoTransportesDeUnAPU(apuItemSubproyecto);
		
		double total = transportes.stream().mapToDouble(Transporte::getValorParcial).sum();
		total = Math.round(total * 100d) / 100d;
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("transportes", transportes);
		model.addAttribute("total", total);
		return "Recursos/Transportes/Ver_Transportes"; //plantilla que carga el metodo
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes/Asignar_Transporte")//URL
	public String mostrarFormularioDeAsignarTransportes(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
		
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		Transporte transporte = new Transporte();
		List<MaterialAPUItemSubproyecto> materiales = materialAPUItemSubproyectoServicio.obtenerMaterialAsignadoNoTransportado(apuItemSubproyectoId);
		
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("transporte", transporte);
		model.addAttribute("materiales", materiales);
		
		return "Recursos/Transportes/Asignar_Transporte";
	}

	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes/Guardar")
	public String guardarTransporte(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("materialAPUItemSubproyectoId") int materialAPUItemSubproyectoId, @RequestParam("distancia") double distancia, @RequestParam("precio") double precio){
		
		int id = servicio.obtenerMaximoId() + 1;
		Transporte transporte = new Transporte(id, materialAPUItemSubproyectoServicio.obtenerMaterialAPUItemSubproyectoPorId(materialAPUItemSubproyectoId), distancia, precio, true);
		servicio.asignarTransporte(transporte);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		model.addAttribute("proyecto", proyectoServicio.obtenerProyectoPorId(proyectoId)); 
		model.addAttribute("subproyecto", subproyectoServicio.obtenerSubproyectoPorId(subproyectoId));
		model.addAttribute("itemSubproyecto", itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId));
		model.addAttribute("apuItemSubproyecto", apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId));
		model.addAttribute("transporte", servicio.obtenerTransportePorId(id));
		return "Recursos/Transportes/Editar_Transporte";

	}
	
	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes/{id}/Actualizar")
	public String actualizarMaterialAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("distancia") double distancia, @RequestParam("precio") double precio){

		
		Transporte transporteExistente = servicio.obtenerTransportePorId(id);
		transporteExistente.setDistancia(distancia);
		transporteExistente.setPrecio(precio);
		servicio.actualizarTransporte(transporteExistente);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes/{id}/Ocultar")
	public String ocultarMaterialAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		servicio.ocultarTransporte(id);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Transportes"; //URL en donde retorna el metodo

	}

}