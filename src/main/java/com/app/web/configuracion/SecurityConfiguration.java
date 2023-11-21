package com.app.web.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.app.web.servicio.UsuarioServicio;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioServicio);
		auth.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		
		return auth;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				"/js/**",
				"/css/**",
				"/imagenes/**").permitAll()
		.antMatchers("/Iniciar_Sesion").anonymous()
		.antMatchers("/Recuperar_Contrasena/**").anonymous()
		.antMatchers("/Recuperar_Contrasena?Exito").anonymous()
		.antMatchers("/Recuperar_Contrasena?Error").anonymous()
		.anyRequest().authenticated()
		.and()
	.formLogin()
		.loginPage("/Iniciar_Sesion")
		.and()
	.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/Iniciar_Sesion?logout")
		.permitAll();
	}
}
