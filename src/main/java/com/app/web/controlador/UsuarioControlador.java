package com.app.web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.web.dto.UsuarioRegistroDTO;
import com.app.web.servicio.UsuarioServicioImpl;

@Controller
public class UsuarioControlador {

	@Autowired
	private UsuarioServicioImpl servicio;

	@GetMapping("/Usuarios")
	public String ListarEditores(Model model) {
		model.addAttribute("usuarios", servicio.listarUsuarios());
		return "Usuarios/Ver_Usuarios";
	}

	@GetMapping("/Usuarios/{correo}/Ocultar")
	public String ocultarUsuario(@PathVariable String correo) {

		servicio.ocultarUsuario(correo); // Se oculta

		return "redirect:/Usuarios"; // URL en donde retorna el metodo

	}

	@GetMapping("/Usuarios/Registrar_Usuario")
	public String mostrarFormularioDeRegistroDeUsuarios(Model model) {

		UsuarioRegistroDTO usuario = new UsuarioRegistroDTO();

		model.addAttribute("usuario", usuario);

		return "Usuarios/Crear_Usuario";
	}

	@PostMapping("/Usuarios/Registrar_Usuario/Guardar")
	public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO, Model model) {

		if (servicio.correoExistenteYVisible(registroDTO.getCorreo())) {

			model.addAttribute("alertaCorreoExistente", "El correo ya existe y es visible.");
			return "Usuarios/Crear_Usuario";
		}
		

		servicio.guardarUsuario(registroDTO);
		
		
		return "redirect:/Usuarios?Exito"; // URL en donde retorna el metodo
	}

}
