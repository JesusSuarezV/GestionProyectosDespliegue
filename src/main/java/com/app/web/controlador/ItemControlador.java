package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.app.web.servicio.ItemServicio;
import com.app.web.entidad.Item;
@Controller
public class ItemControlador {

	@Autowired
	private ItemServicio servicio;

	@GetMapping({ "/Items" }) //URL
	public String listarItems(Model model) {

		model.addAttribute("items", servicio.listarTodosLosItems()); //Se listan todos los items visibles
		return "Items/Ver_Items"; //URL en donde retorna el metodo
	}

	@GetMapping("/Items/Nuevo_Item")//URL
	public String mostrarFormularioDeRegistrarItems(Model modelo) {

		Item item = new Item(); //se crea un proyecto vacio
		modelo.addAttribute("item", item); //se envia al modelo
		return "/Items/Crear_Item"; //URL en donde retorna el metodo

	}

	@PostMapping("/Items/Guardar")
	public String guardarItem(@RequestParam("nombre") String nombre){

		int id = servicio.obtenerMaximoId() + 1; //se autogenera el id
		Item item = new Item(id, nombre, true); //se crea el item
		servicio.guardarItem(item);//se guarda el item
		return "redirect:/Items"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/Items/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("item", servicio.obtenerItemPorId(id)); //se envia el item que se va a editar
		return "Items/Editar_Item"; //URL en donde retorna el metodo
	}
	
	@PostMapping("/Items/{id}/Actualizar")
	public String actualizarItem(@PathVariable int id, @RequestParam("nombre") String nombre){
		Item itemExistente = servicio.obtenerItemPorId(id); //se obtiene el item

		itemExistente.setNombre(nombre); //se le cambia el atributo

		servicio.actualizarItem(itemExistente); //se guarda

		return "redirect:/Items"; //URL en donde retorna el metodo
	}
	@GetMapping("/Items/{id}/Ocultar")
	public String ocultarItem(@PathVariable int id) {

		servicio.ocultarItem(id); //Se oculta

		return "redirect:/Items"; //URL en donde retorna el metodo

	}

}