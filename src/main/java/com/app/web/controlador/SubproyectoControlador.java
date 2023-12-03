package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.web.servicio.APUItemSubproyectoServicio;
import com.app.web.servicio.APUServicio;
import com.app.web.servicio.ItemSubproyectoServicio;
import com.app.web.servicio.ManoObraAPUItemSubproyectoServicio;
import com.app.web.servicio.MaquinariaAPUItemSubproyectoServicio;
import com.app.web.servicio.MaterialAPUItemSubproyectoServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.servicio.TransporteServicio;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Transporte;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.APUItemSubproyecto;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class SubproyectoControlador {

	@Autowired
	private SubproyectoServicio servicio;
	@Autowired
	private ProyectoServicio proyectoServicio;
	@Autowired
	private APUItemSubproyectoServicio apuItemSubproyectoServicio;
	@Autowired
	private ItemSubproyectoServicio itemSubproyectoServicio;
	@Autowired
	private MaterialAPUItemSubproyectoServicio materialAPUItemSubproyectoServicio;
	@Autowired
	private TransporteServicio transporteServicio;
	@Autowired
	private MaquinariaAPUItemSubproyectoServicio maquinariaAPUItemSubproyectoServicio;
	@Autowired
	private ManoObraAPUItemSubproyectoServicio manoObraAPUItemSubproyectoServicio;

	@GetMapping({ "/Proyectos/{proyectoId}/Subproyectos" }) //URL
	public String listarSubproyectosDeUnProyecto(@PathVariable int proyectoId, Model model) { //Se captura el id del proyecto mediante la URL
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId); //Se obtiene el proyecto mediante el id
		List<Subproyecto> subproyectos = servicio.listarTodosLosSubproyectosDeUnProyecto(proyecto); //se listan los Subproyectos de dicho proyecto
		
		model.addAttribute("proyecto", proyecto); //se envia el proyecto en cuestion
		model.addAttribute("subproyectos", subproyectos);//Se envian todos los subproyectos visibles de ese proyecto
		model.addAttribute("servicio", servicio);
		
		return "Subproyectos/Ver_Subproyectos"; //plantilla que carga el metodo
	}
	
	

	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/Nuevo_Subproyecto")//URL
	public String mostrarFormularioDeRegistrarSubproyectos(@PathVariable int proyectoId, Model modelo) {
		Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId); //Se obtiene el proyecto mediante el id
		Subproyecto subproyecto = new Subproyecto(); //se crea un subproyecto vacio
		modelo.addAttribute("Subproyecto", subproyecto); //se envia al modelo
		modelo.addAttribute("proyecto", proyecto);
		return "Subproyectos/Crear_Subproyecto"; //plantilla que carga el metodo

	}

	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/Guardar")
	public String guardarSubproyecto(@PathVariable("proyectoId") int proyectoId, @RequestParam("nombre") String nombre){

		int id = servicio.obtenerMaximoId() + 1; //se autogenera el id
		Subproyecto subproyecto = new Subproyecto(id, nombre, proyectoServicio.obtenerProyectoPorId(proyectoId), true); //se crea el subproyecto
		servicio.guardarSubproyecto(subproyecto);//se guarda el subproyecto
		return "redirect:/Proyectos/{proyectoId}/Subproyectos"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int proyectoId, @PathVariable int id, Model modelo) {
		modelo.addAttribute("proyecto", proyectoServicio.obtenerProyectoPorId(proyectoId)); //se envia el proyecto en el que se esta trabajando
		modelo.addAttribute("subproyecto", servicio.obtenerSubproyectoPorId(id)); //se envia el subproyecto que se va a editar
		return "Subproyectos/Editar_Subproyecto"; //URL en donde retorna el metodo
	}
	
	@PostMapping("/Proyectos/{proyectoId}/Subproyectos/{id}/Actualizar")
	public String actualizarSubproyecto(@PathVariable int id, @RequestParam("nombre") String nombre){
		Subproyecto subProyectoExistente = servicio.obtenerSubproyectoPorId(id); //se obtiene el subproyecto

		subProyectoExistente.setNombre(nombre); //se le cambia el atributo

		servicio.actualizarSubproyecto(subProyectoExistente); //se guarda

		return "redirect:/Proyectos/{proyectoId}/Subproyectos"; //URL en donde retorna el metodo
	}
	@GetMapping("/Proyectos/{proyectoId}/Subproyectos/{id}/Ocultar")
	public String ocultarSubproyecto(@PathVariable int id) {

		servicio.ocultarSubproyecto(id); //Se oculta

		return "redirect:/Proyectos/{proyectoId}/Subproyectos"; //URL en donde retorna el metodo

	}

}