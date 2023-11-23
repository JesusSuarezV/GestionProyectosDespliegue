package com.app.web.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginControlador {

	
	@GetMapping({"/Iniciar_Sesion"})
	public String iniciarSesion() {
		return "Login/Login";
	}
	
	@GetMapping("/")
	public String verPaginaDeInicio(Model modelo) {
		return "Index";
	}
	
}
