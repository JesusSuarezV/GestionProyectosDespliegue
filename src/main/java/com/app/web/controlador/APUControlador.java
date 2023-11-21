package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.app.web.servicio.APUServicio;
import com.app.web.entidad.APU;
@Controller
public class APUControlador {

	@Autowired
	private APUServicio servicio;

	@GetMapping({ "/APU" }) //URL
	public String listarAPU(Model model) {

		model.addAttribute("apu", servicio.listarTodosLosAPU()); //Se listan todos los APU visibles
		return "APU/Ver_APU"; //URL en donde retorna el metodo
	}

	@GetMapping("/APU/Nuevo_APU")//URL
	public String mostrarFormularioDeRegistrarAPU(Model modelo) {

		APU apu = new APU(); //se crea un apu vacio
		modelo.addAttribute("apu", apu); //se envia al modelo
		return "/APU/Crear_APU"; //URL en donde retorna el metodo

	}

	@PostMapping("/APU/Guardar")
	public String guardarAPU(@RequestParam("nombre") String nombre, @RequestParam("unidad") String unidad){

		int id = servicio.obtenerMaximoId() + 1; //se autogenera el id
		APU apu = new APU(id, nombre, unidad, true); //se crea el apu
		servicio.guardarAPU(apu);//se guarda el apu
		return "redirect:/APU"; //URL en donde retorna el metodo
		
	}
	
	@GetMapping("/APU/{id}/Editar")
	public String mostrarFormularioDeEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("apu", servicio.obtenerAPUPorId(id)); //se envia el APU que se va a editar
		return "APU/Editar_APU"; //URL en donde retorna el metodo
	}
	
	@PostMapping("/APU/{id}/Actualizar")
	public String actualizarAPU(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("unidad") String unidad){
		APU apuExistente = servicio.obtenerAPUPorId(id); //se obtiene el apu

		apuExistente.setNombre(nombre); //se le cambia el atributo
		apuExistente.setUnidad(unidad);

		servicio.actualizarAPU(apuExistente); //se guarda

		return "redirect:/APU"; //URL en donde retorna el metodo
	}
	@GetMapping("/APU/{id}/Ocultar")
	public String ocultarProyecto(@PathVariable int id) {

		servicio.ocultarAPU(id); //Se oculta

		return "redirect:/APU"; //URL en donde retorna el metodo

	}

}