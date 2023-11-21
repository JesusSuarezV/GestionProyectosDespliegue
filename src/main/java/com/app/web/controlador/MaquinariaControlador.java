package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.app.web.servicio.MaquinariaServicio;
import com.app.web.entidad.Maquinaria;
@Controller
public class MaquinariaControlador {

	@Autowired
	private MaquinariaServicio servicio;

	@GetMapping({ "/Maquinarias" }) //URL
	public String listarMaquinaria(Model model) {

		model.addAttribute("maquinarias", servicio.listarTodasLasMaquinarias()); //Se listan todas las maquinarias visibles
		return "Maquinarias/Ver_Maquinarias"; //URL en donde retorna el metodo
	}

	@GetMapping("/Maquinarias/Nueva_Maquinaria")//URL
	public String mostrarFormularioDeRegistrarMaquinarias(Model modelo) {

		Maquinaria maquinaria = new Maquinaria(); //se crea una maquinaria vacia
		modelo.addAttribute("maquinaria", maquinaria); //se envia al modelo
		return "/Maquinarias/Crear_Maquinaria"; //URL en donde retorna el metodo

	}

	@PostMapping("/Maquinarias/Guardar")
	public String guardarMaquinaria(@RequestParam("nombre") String nombre, @RequestParam("unidad") String unidad){

		int id = servicio.obtenerMaximoId() + 1; //se autogenera el id
		Maquinaria maquinaria = new Maquinaria(id, nombre, unidad, true); //se crea la maquinaria
		servicio.guardarMaquinaria(maquinaria);//se guarda la maquinaria
		return "redirect:/Maquinarias"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Maquinarias/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("maquinaria", servicio.obtenerMaquinariaPorId(id)); //se envia la maquinaria que se va a editar
		return "Maquinarias/Editar_Maquinaria"; //URL en donde retorna el metodo
	}
	
	@PostMapping("/Maquinarias/{id}/Actualizar")
	public String actualizarMaquinaria(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("unidad") String unidad){
		Maquinaria maquinariaExistente = servicio.obtenerMaquinariaPorId(id); //se obtiene la maquinaria

		maquinariaExistente.setNombre(nombre); //se le cambia el atributo
		maquinariaExistente.setUnidad(unidad);
		
		servicio.actualizarMaquinaria(maquinariaExistente); //se guarda

		return "redirect:/Maquinarias"; //URL en donde retorna el metodo
	}
	@GetMapping("/Maquinarias/{id}/Ocultar")
	public String ocultarMaquinarias(@PathVariable int id) {

		servicio.ocultarMaquinaria(id); //Se oculta

		return "redirect:/Maquinarias"; //URL en donde retorna el metodo

	}

}