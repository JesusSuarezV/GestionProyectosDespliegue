package com.app.web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class RecoveryServicio {

	@Autowired
	private JavaMailSender mailSender;
	
	public void enviarCorreo(String toEmail, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gestionproyectosrecovery@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailSender.send(message);
		
		System.out.println("Correo Enviado");
	}
}
