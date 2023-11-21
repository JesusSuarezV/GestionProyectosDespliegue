package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.app.web.servicio.ProyectoServicio;
import com.app.web.entidad.Proyecto;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ProyectoControlador {

	@Autowired
	private ProyectoServicio servicio;

	@GetMapping({ "/Proyectos" }) //URL
	public String listarProyectos(Model model) {

		model.addAttribute("proyectos", servicio.listarTodosLosProyectos()); //Se listan todos los proyectos visibles
		return "Proyectos/Ver_Proyectos"; //plantilla que carga el metodo
	}

	@GetMapping("/Proyectos/Nuevo_Proyecto")//URL
	public String mostrarFormularioDeRegistrarProyectos(Model modelo) {

		Proyecto proyecto = new Proyecto(); //se crea un proyecto vacio
		modelo.addAttribute("proyecto", proyecto); //se envia al modelo
		return "/Proyectos/Crear_Proyecto"; //plantilla que carga el metodo

	}

	@PostMapping("/Proyectos/Guardar")
	public String guardarProyecto(@RequestParam("nombre") String nombre, @RequestParam("ubicacion") String ubicacion){

		int id = servicio.obtenerMaximoId() + 1; //se autogenera el id
		Proyecto proyecto = new Proyecto(id, nombre, ubicacion, true); //se crea el proyecto
		servicio.guardarProyecto(proyecto);//se guarda el proyecto
		return "redirect:/Proyectos"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Proyectos/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("proyecto", servicio.obtenerProyectoPorId(id)); //se envia el proyecto que se va a editar
		return "Proyectos/Editar_Proyecto"; //URL en donde retorna el metodo
	}
	
	@PostMapping("/Proyectos/{id}/Actualizar")
	public String actualizarProyecto(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("ubicacion") String ubicacion){
		Proyecto proyectoExistente = servicio.obtenerProyectoPorId(id); //se obtiene el proyecto

		proyectoExistente.setNombre(nombre); //se le cambia el atributo
		
		proyectoExistente.setUbicacion(ubicacion);

		servicio.actualizarProyecto(proyectoExistente); //se guarda

		return "redirect:/Proyectos"; //URL en donde retorna el metodo
	}
	@GetMapping("/Proyectos/{id}/Ocultar")
	public String ocultarProyecto(@PathVariable int id) {

		servicio.ocultarProyecto(id); //Se oculta

		return "redirect:/Proyectos"; //URL en donde retorna el metodo

	}

}