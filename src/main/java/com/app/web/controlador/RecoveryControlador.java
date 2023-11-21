package com.app.web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.web.dto.UsuarioRegistroDTO;
import com.app.web.entidad.Usuario;
import com.app.web.servicio.RecoveryServicio;
import com.app.web.servicio.UsuarioServicioImpl;


@Controller
public class RecoveryControlador {
	
	@Autowired
	private UsuarioServicioImpl servicio;
	
	@Autowired
	private RecoveryServicio recoveryServicio;

	
	@GetMapping("/Recuperar_Contrasena")
	public String mostrarFormularioDeRecuperarContraseña() {
		
		return "Recovery/Recuperar_Contrasena";
	}
	
	@PostMapping("/Recuperar_Contrasena/Validar")
	public String recuperarContraseña(@RequestParam("correo") String correo){
		
		
		if(!servicio.correoExistenteYVisible(correo)) {
			return "redirect:/Recuperar_Contrasena?Error";
		}
		
		Usuario usuario = servicio.obtenerUsuarioPorCorreo(correo);
		
		String toEmail = usuario.getCorreo();
		
		String Asunto = "Recuperar Contraseña";
		
		String Cuerpo = "Estimado "+ usuario.getNombre() + " " + usuario.getApellido() + ". Su contraseña es: " + usuario.getContraseña();
		
		recoveryServicio.enviarCorreo(toEmail, correo, Cuerpo);
		
		return "redirect:/Recuperar_Contrasena?Exito";
	}
	
	
}
