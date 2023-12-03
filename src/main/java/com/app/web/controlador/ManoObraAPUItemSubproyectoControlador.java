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
import com.app.web.servicio.ManoObraServicio;
import com.app.web.servicio.MaquinariaAPUItemSubproyectoServicio;
import com.app.web.servicio.MaquinariaServicio;
import com.app.web.servicio.MaterialAPUItemSubproyectoServicio;
import com.app.web.servicio.MaterialServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.APU;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObra;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.Maquinaria;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.Material;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ManoObraAPUItemSubproyectoControlador {

	@Autowired
	private ManoObraAPUItemSubproyectoServicio servicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private ProyectoServicio proyectoServicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;
	@Autowired
	private ManoObraServicio manoObraServicio;
	
	@GetMapping({ "/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras" }) 
	public String listarManoObrasDeUnAPU(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
	
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		List<ManoObraAPUItemSubproyecto> manoObras = servicio.listarTodasLasManosDeObraDeUnAPU(apuItemSubproyecto);
		
		double total = manoObras.stream().mapToDouble(ManoObraAPUItemSubproyecto::getValorParcial).sum();
		total = Math.round(total * 100d) / 100d;
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("manoObras", manoObras);
		model.addAttribute("total", total);
		return "Recursos/ManoObras/Ver_ManoObras"; //plantilla que carga el metodo
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras/Asignar_ManoObra")//URL
	public String mostrarFormularioDeAsignarManoObras(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
		
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto = new ManoObraAPUItemSubproyecto();
		List<ManoObra> manoObras= manoObraServicio.obtenerManoObraNoAsignados(apuItemSubproyectoId);
		
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("manoObraAPUItemSubproyecto", manoObraAPUItemSubproyecto);
		model.addAttribute("manoObras", manoObras);
		
		return "Recursos/ManoObras/Asignar_ManoObra";
	}

	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras/Guardar")
	public String guardarManoObraAPUItemSubproyecto(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("manoObraId") int manoObraId, @RequestParam("cantidad") int cantidad, @RequestParam("jornal") double jornal, @RequestParam("prestacion") double prestacion,@RequestParam("rdto") double rdto){

		int id = servicio.obtenerMaximoId() + 1;
		ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto = new ManoObraAPUItemSubproyecto(id, apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId), manoObraServicio.obtenerManoObrasPorId(manoObraId), jornal, prestacion, rdto, cantidad, true);
		servicio.asignarManoDeObra(manoObraAPUItemSubproyecto);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		model.addAttribute("proyecto", proyectoServicio.obtenerProyectoPorId(proyectoId)); 
		model.addAttribute("subproyecto", subproyectoServicio.obtenerSubproyectoPorId(subproyectoId));
		model.addAttribute("itemSubproyecto", itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId));
		model.addAttribute("apuItemSubproyecto", apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId));
		model.addAttribute("manoObra", servicio.obtenerManoObraAPUItemSubproyectoPorId(id));
		return "Recursos/ManoObras/Editar_ManoObra";

	}
	
	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras/{id}/Actualizar")
	public String actualizarManoObraAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("cantidad") int cantidad, @RequestParam("jornal") double jornal, @RequestParam("prestacion") double prestacion,@RequestParam("rdto") double rdto){
		
		ManoObraAPUItemSubproyecto manoObraAPUItemSubproyectoExistente = servicio.obtenerManoObraAPUItemSubproyectoPorId(id);
		manoObraAPUItemSubproyectoExistente.setCantidad(cantidad);
		manoObraAPUItemSubproyectoExistente.setJornal(jornal);
		manoObraAPUItemSubproyectoExistente.setPrestacion(prestacion);
		servicio.actualizarManoDeObraAsignada(manoObraAPUItemSubproyectoExistente);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras/{id}/Ocultar")
	public String ocultarManoObraAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		servicio.ocultarManoObraAPUItemSubproyecto(id);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/ManoObras"; //URL en donde retorna el metodo

	}

}