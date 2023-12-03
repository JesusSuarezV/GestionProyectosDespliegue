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
import com.app.web.servicio.MaquinariaAPUItemSubproyectoServicio;
import com.app.web.servicio.MaquinariaServicio;
import com.app.web.servicio.MaterialAPUItemSubproyectoServicio;
import com.app.web.servicio.MaterialServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.APU;
import com.app.web.entidad.ItemSubproyecto;
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
public class MaquinariaAPUItemSubproyectoControlador {

	@Autowired
	private MaquinariaAPUItemSubproyectoServicio servicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private ProyectoServicio proyectoServicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;
	@Autowired
	private MaquinariaServicio maquinariaServicio;
	
	@GetMapping({ "/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias" }) 
	public String listarMaterialesDeUnAPU(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
	
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		List<MaquinariaAPUItemSubproyecto> maquinarias = servicio.listarTodasLasMaquinariasDeUnAPU(apuItemSubproyecto);
		
		double total = maquinarias.stream().mapToDouble(MaquinariaAPUItemSubproyecto::getValorParcial).sum();
		total = Math.round(total * 100d) / 100d;
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("maquinarias", maquinarias);
		model.addAttribute("total", total);
		return "Recursos/Maquinarias/Ver_Maquinarias"; //plantilla que carga el metodo
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias/Asignar_Maquinaria")//URL
	public String mostrarFormularioDeAsignarMateriales(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
		
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto = new MaquinariaAPUItemSubproyecto();
		List<Maquinaria> maquinarias = maquinariaServicio.obtenerMaquinariaNoAsignadas(apuItemSubproyectoId);
		
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("maquinariaAPUItemSubproyecto", maquinariaAPUItemSubproyecto);
		model.addAttribute("maquinarias", maquinarias);
		
		return "Recursos/Maquinarias/Asignar_Maquinaria";
	}

	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias/Guardar")
	public String guardarMaquinariaAPUItemSubproyecto(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("maquinariaId") int maquinariaId, @RequestParam("tarifa") double tarifa, @RequestParam("rdto") double rdto){

		int id = servicio.obtenerMaximoId() + 1;
		MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto = new MaquinariaAPUItemSubproyecto(id, apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId), maquinariaServicio.obtenerMaquinariaPorId(maquinariaId), tarifa, rdto, true);
		servicio.asignarMaquinaria(maquinariaAPUItemSubproyecto);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		model.addAttribute("proyecto", proyectoServicio.obtenerProyectoPorId(proyectoId)); 
		model.addAttribute("subproyecto", subproyectoServicio.obtenerSubproyectoPorId(subproyectoId));
		model.addAttribute("itemSubproyecto", itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId));
		model.addAttribute("apuItemSubproyecto", apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId));
		model.addAttribute("maquinaria", servicio.obtenerMaquinariaAPUItemSubproyectoPorId(id));
		return "Recursos/Maquinarias/Editar_Maquinaria";

	}
	
	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias/{id}/Actualizar")
	public String actualizarMaquinariaAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("tarifa") double tarifa, @RequestParam("rdto") double rdto){

		MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyectoExistente = servicio.obtenerMaquinariaAPUItemSubproyectoPorId(id);
		maquinariaAPUItemSubproyectoExistente.setTarifa(tarifa);
		maquinariaAPUItemSubproyectoExistente.setRdto(rdto);
		servicio.actualizarMaquinariaAsignada(maquinariaAPUItemSubproyectoExistente);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias/{id}/Ocultar")
	public String ocultarMaterialAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		servicio.ocultarMaquinariaAPUItemSubproyecto(id);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Maquinarias"; //URL en donde retorna el metodo

	}

}