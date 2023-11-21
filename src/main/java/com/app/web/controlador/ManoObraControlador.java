package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.app.web.servicio.ManoObraServicio;
import com.app.web.entidad.ManoObra;

import javax.servlet.http.HttpServletResponse;


@Controller
public class ManoObraControlador {

	@Autowired
	private ManoObraServicio servicio;

	@GetMapping({ "/ManoObras" }) //URL
	public String listarManoObras(Model model) {

		model.addAttribute("manoObras", servicio.listarTodosLasManoObras()); //Se listan todas las Mano de Obra visibles
		return "ManoObras/Ver_ManoObras"; //URL en donde retorna el metodo
	}

	@GetMapping("/ManoObras/Nueva_ManoObra")//URL
	public String mostrarFormularioDeRegistrarManoObras(Model modelo) {
		
		ManoObra manoObra = new ManoObra(); //se crea una Mano de Obra vacia
		modelo.addAttribute("manoObra", manoObra); //se envia al modelo
		return "/ManoObras/Crear_ManoObra"; //URL en donde retorna el metodo

	}

	@PostMapping("/ManoObras/Guardar")
	public String guardarManoObra(@RequestParam("nombre") String nombre){

		int id = servicio.obtenerMaximoId() + 1; //se autogenera el id
		ManoObra manoObra = new ManoObra(id, nombre, true); //se crea el proyecto
		servicio.guardarManoObra(manoObra);//se guarda la mano de obra
		return "redirect:/ManoObras"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/ManoObras/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("manoObra", servicio.obtenerManoObrasPorId(id)); //se envia la mano de obra que se va a editar
		return "ManoObras/Editar_ManoObra"; //URL en donde retorna el metodo
	}
	
	@PostMapping("/ManoObras/{id}/Actualizar")
	public String actualizarManoObra(@PathVariable int id, @RequestParam("nombre") String nombre){
		ManoObra manoObraExistente = servicio.obtenerManoObrasPorId(id); //se obtiene la mano de obra

		manoObraExistente.setNombre(nombre); //se le cambia el atributo

		servicio.actualizarManoObra(manoObraExistente); //se guarda

		return "redirect:/ManoObras"; //URL en donde retorna el metodo
	}
	@GetMapping("/ManoObras/{id}/Ocultar")
	public String ocultarManoObra(@PathVariable int id) {

		servicio.ocultarManoObra(id); //Se oculta

		return "redirect:/ManoObras"; //URL en donde retorna el metodo

	}

}