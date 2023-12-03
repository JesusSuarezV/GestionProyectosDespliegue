package com.app.web.controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.web.servicio.APUItemSubproyectoServicio;
import com.app.web.servicio.ItemSubproyectoServicio;
import com.app.web.servicio.ManoObraAPUItemSubproyectoServicio;
import com.app.web.servicio.MaquinariaAPUItemSubproyectoServicio;
import com.app.web.servicio.MaterialAPUItemSubproyectoServicio;
import com.app.web.servicio.ProyectoServicio;
import com.app.web.servicio.SubproyectoServicio;
import com.app.web.servicio.TransporteServicio;
import com.app.web.entidad.APUItemSubproyecto;
import com.app.web.entidad.ItemSubproyecto;
import com.app.web.entidad.ManoObraAPUItemSubproyecto;
import com.app.web.entidad.MaquinariaAPUItemSubproyecto;
import com.app.web.entidad.MaterialAPUItemSubproyecto;
import com.app.web.entidad.Proyecto;
import com.app.web.entidad.Subproyecto;
import com.app.web.entidad.Transporte;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ProyectoControlador {

	@Autowired
	private ProyectoServicio servicio;
	@Autowired
	private SubproyectoServicio subproyectoServicio;
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

	@GetMapping({ "/Proyectos" }) //URL
	public String listarProyectos(Model model) {

		model.addAttribute("proyectos", servicio.listarTodosLosProyectos()); //Se listan todos los proyectos visibles
		return "Proyectos/Ver_Proyectos"; //plantilla que carga el metodo
	}

	@GetMapping("/Proyectos/Nuevo_Proyecto")//URL
	public String mostrarFormularioDeRegistrarProyectos(Model modelo) {

		Proyecto proyecto = new Proyecto(); //se crea un proyecto vacio
		modelo.addAttribute("proyecto", proyecto); //se envia al modelo
		return "Proyectos/Crear_Proyecto"; //plantilla que carga el metodo

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
	
	@GetMapping("/Proyectos/{id}/Visualizar")
	public String visualizarProyecto(@PathVariable int id, Model model) {

		double item = 0;
		double subItem = 0;
		double apu = 0;
		double apuSum = 0;
		double valorUnitario = 0;
		double valorParcial = 0;
		double valorCapitulo = 0;
		double subtotalSubproyecto = 0;
		double totalProyecto = 0;
		String elementos = "";
		String elementosAux = "";
		Proyecto proyecto = servicio.obtenerProyectoPorId(id); //se obtiene el proyecto
		List<Subproyecto> subproyectos = subproyectoServicio.listarTodosLosSubproyectosDeUnProyecto(proyecto);
		for (Subproyecto subproyecto : subproyectos) {
			subtotalSubproyecto = 0;
			item = 0;
			elementos += "<tr><td colspan='7'>" + subproyecto.getNombre() + "</td></tr>";
			List<ItemSubproyecto> itemSubproyectos = itemSubproyectoServicio.listarTodosLosItemsDeUnSubproyecto(subproyecto);
			for (ItemSubproyecto itemSubproyecto : itemSubproyectos) {
				valorCapitulo = 0;
				
				item += 1.0;
				apu = 0;
				apuSum = 0.1;
				List<APUItemSubproyecto> apuItemSubproyectos = apuItemSubproyectoServicio.listarTodosLosAPUDeUnItemDeUnSubproyecto(itemSubproyecto);
				for (APUItemSubproyecto apuItemSubproyecto : apuItemSubproyectos) {
					subItem = item;
					apu += apuSum;
					valorUnitario = 0;
					List<MaterialAPUItemSubproyecto> materiales = materialAPUItemSubproyectoServicio.listarTodosLosMaterialesDeUnAPU(apuItemSubproyecto);
					valorUnitario += materiales.stream().mapToDouble(MaterialAPUItemSubproyecto::getValorParcial).sum();
					List<Transporte> transportes = transporteServicio.listarTodosLoTransportesDeUnAPU(apuItemSubproyecto);
					valorUnitario += transportes.stream().mapToDouble(Transporte::getValorParcial).sum();
					List<MaquinariaAPUItemSubproyecto> maquinarias = maquinariaAPUItemSubproyectoServicio.listarTodasLasMaquinariasDeUnAPU(apuItemSubproyecto);
					valorUnitario += maquinarias.stream().mapToDouble(MaquinariaAPUItemSubproyecto::getValorParcial).sum();
					List<ManoObraAPUItemSubproyecto> manoObras = manoObraAPUItemSubproyectoServicio.listarTodasLasManosDeObraDeUnAPU(apuItemSubproyecto);
					valorUnitario += manoObras.stream().mapToDouble(ManoObraAPUItemSubproyecto::getValorParcial).sum();
					valorUnitario = Math.round(valorUnitario * 100d) / 100d;
					valorParcial = valorUnitario * apuItemSubproyecto.getCantidad();
					valorParcial = Math.round(valorParcial * 100d) / 100d;
					valorCapitulo += valorParcial;
					if (apu == 1) {
						apu = 0.10;
						apuSum = apuSum/10;
					}
					
					subItem += apu;
					
					elementosAux += "<tr><td>"+ subItem +"</td><td>" + apuItemSubproyecto.getApu().getNombre() + "</td><td>"+ apuItemSubproyecto.getApu().getUnidad() +
							"</td><td>" + apuItemSubproyecto.getCantidad() + "</td><td>" + valorUnitario + "</td><td>" + valorParcial + "</td><td></td></tr>";
				}
				
				item = 	Math.floor(item);
				elementos += "<tr><td>"+ item +"</td><td>" + itemSubproyecto.getItem().getNombre() + "</td><td></td><td></td><td></td><td></td><td>" + valorCapitulo + "</td></tr>" + elementosAux;
				subtotalSubproyecto += valorCapitulo;
				elementosAux = "";
			}
			elementos += "<tr><td></td><td class='texto-izquierda' colspan = '5'>SUBTOTAL " + subproyecto.getNombre() + "</td><td>" + subtotalSubproyecto + "</td></tr>";
			totalProyecto += subtotalSubproyecto;
		}
		elementos += "<tr><td></td><td class='texto-izquierda' colspan = '5'>COSTO TOTAL DEL PROYECTO</td><td>" + totalProyecto + "</td></tr>";
	
		model.addAttribute("proyecto", proyecto);
		model.addAttribute("subproyectos", subproyectos);
		model.addAttribute("elementos", elementos);
		
		return "Proyectos/Visualizar_Proyecto";

	}
	

}