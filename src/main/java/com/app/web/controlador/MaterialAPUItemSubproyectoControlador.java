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
import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.APU;
import com.app.web.entidad.ItemSubproyecto;
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
public class MaterialAPUItemSubproyectoControlador {

	@Autowired
	private MaterialAPUItemSubproyectoServicio servicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private ProyectoServicio proyectoServicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;
	@Autowired
	private MaterialServicio materialServicio;
	
	@GetMapping({ "/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales" }) 
	public String listarMaterialesDeUnAPU(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
	
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		List<MaterialAPUItemSubproyecto> materiales = servicio.listarTodosLosMaterialesDeUnAPU(apuItemSubproyecto);
		
		double total = materiales.stream().mapToDouble(MaterialAPUItemSubproyecto::getValorParcial).sum();
		total = Math.round(total * 100d) / 100d;
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("materiales", materiales);
		model.addAttribute("total", total);
		return "Recursos/Materiales/Ver_Materiales"; //plantilla que carga el metodo
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales/Asignar_Material")//URL
	public String mostrarFormularioDeAsignarMateriales(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId,Model model) {
		
		
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
		Subproyecto subproyecto = subproyectoServicio.obtenerSubproyectoPorId(subproyectoId);
		ItemSubproyecto itemSubproyecto = itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId);
		APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId);
		MaterialAPUItemSubproyecto materialAPUItemSubproyecto = new MaterialAPUItemSubproyecto();
		List<Material> materiales = materialServicio.obtenerMaterialNoAsignados(apuItemSubproyectoId);
		
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyecto", subproyecto);
		model.addAttribute("itemSubproyecto", itemSubproyecto);
		model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
		model.addAttribute("materialAPUItemSubproyecto", materialAPUItemSubproyecto);
		model.addAttribute("materiales", materiales);
		
		return "Recursos/Materiales/Asignar_Material";
	}

	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales/Guardar")
	public String guardarMaterialAPUItemSubproyecto(@PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("materialId") int materialId, @RequestParam("proveedor") int proveedor, @RequestParam("cantidad") double cantidad, @RequestParam("valorUnitario") double valorUnitario){

		int id = servicio.obtenerMaximoId() + 1;
		MaterialAPUItemSubproyecto materialAPUItemSubproyecto = new MaterialAPUItemSubproyecto(id, apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId), materialServicio.obtenerMaterialPorId(materialId), proveedor, valorUnitario, cantidad, true);
		servicio.asignarMaterial(materialAPUItemSubproyecto);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		model.addAttribute("proyecto", proyectoServicio.obtenerProyectoPorId(proyectoId)); 
		model.addAttribute("subproyecto", subproyectoServicio.obtenerSubproyectoPorId(subproyectoId));
		model.addAttribute("itemSubproyecto", itemSubproyectoServicio.obtenerItemSubproyectoPorId(itemSubproyectoId));
		model.addAttribute("apuItemSubproyecto", apuItemSubproyectoServicio.obtenerAPUItemSubproyectoPorId(apuItemSubproyectoId));
		model.addAttribute("material", servicio.obtenerMaterialAPUItemSubproyectoPorId(id));
		return "Recursos/Materiales/Editar_Material";

	}
	
	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales/{id}/Actualizar")
	public String actualizarMaterialAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, @RequestParam("proveedor") int proveedor, @RequestParam("cantidad") double cantidad, @RequestParam("valorUnitario") double valorUnitario){

		MaterialAPUItemSubproyecto materialAPUItemSubproyectoExistente = servicio.obtenerMaterialAPUItemSubproyectoPorId(id);
		materialAPUItemSubproyectoExistente.setProveedor(proveedor);
		materialAPUItemSubproyectoExistente.setCantidad(cantidad);
		materialAPUItemSubproyectoExistente.setValorUnitario(valorUnitario);
		servicio.actualizarMaterialAsignado(materialAPUItemSubproyectoExistente);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales/{id}/Ocultar")
	public String ocultarMaterialAPUItemSubproyecto(@PathVariable int id, @PathVariable int proyectoId, @PathVariable int subproyectoId, @PathVariable int itemSubproyectoId, @PathVariable int apuItemSubproyectoId, Model model) {

		servicio.ocultarMaterialAPUItemSubproyecto(id);
		return "redirect:/Proyectos/{proyectoId}/Subproyectos/{subproyectoId}/Items/{itemSubproyectoId}/APU/{apuItemSubproyectoId}/Recursos/Materiales"; //URL en donde retorna el metodo

	}

}