package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.app.web.servicio.MaterialServicio;
import com.app.web.entidad.Material;
@Controller
public class MaterialControlador {

	@Autowired
	private MaterialServicio servicio;

	@GetMapping({ "/Materiales" }) //URL
	public String listarMateriales(Model model) {

		model.addAttribute("materiales", servicio.listarTodosLosMateriales()); //Se listan todos los materiales visibles
		return "Materiales/Ver_Materiales"; //URL en donde retorna el metodo
	}

	@GetMapping("/Materiales/Nuevo_Material")//URL
	public String mostrarFormularioDeRegistrarMateriales(Model modelo) {

		Material material = new Material(); //se crea un material vacio
		modelo.addAttribute("material", material); //se envia al modelo
		return "/Materiales/Crear_Material"; //URL en donde retorna el metodo

	}

	@PostMapping("/Materiales/Guardar")
	public String guardarMaterial(@RequestParam("nombre") String nombre, @RequestParam("unidadMetrica") String unidadMetrica, @RequestParam("observacion") String observacion){

		int id = servicio.obtenerMaximoId() + 1; //se autogenera el id
		Material material = new Material(id, nombre, unidadMetrica, observacion, true); //se crea el material
		servicio.guardarMaterial(material);//se guarda el material
		return "redirect:/Materiales"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Materiales/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("material", servicio.obtenerMaterialPorId(id)); //se envia el material que se va a editar
		return "Materiales/Editar_Material"; //URL en donde retorna el metodo
	}
	
	@PostMapping("/Materiales/{id}/Actualizar")
	public String actualizarMaterial(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("unidadMetrica") String unidadMetrica, @RequestParam("observacion") String observacion){
		Material materialExistente = servicio.obtenerMaterialPorId(id); //se obtiene el material

		materialExistente.setNombre(nombre); //se le cambia el atributo
		materialExistente.setUnidadMetrica(unidadMetrica);
		materialExistente.setObservacion(observacion);
		
		servicio.actualizarMaterial(materialExistente); //se guarda

		return "redirect:/Materiales"; //URL en donde retorna el metodo
	}
	@GetMapping("/Materiales/{id}/Ocultar")
	public String ocultarMaterial(@PathVariable int id) {

		servicio.ocultarMaterial(id); //Se oculta

		return "redirect:/Materiales"; //URL en donde retorna el metodo

	}

}