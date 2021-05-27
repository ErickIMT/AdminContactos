package com.admincontactos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.admincontactos.modelo.ContactoEntity;
import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.servicio.ContactoServicio;
import com.admincontactos.servicio.UsuarioServicio;

@SpringBootApplication
public class AdminContactosApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(AdminContactosApplication.class, args);
		
		//crearUsuario(ctx);
		//crearContacto(ctx);
		//actualizarUsuario(ctx);
		//eliminarUsuario(ctx);
	}

	/**
	 * Metodo para crear Nuevo Usuario

	public static void crearUsuario(ConfigurableApplicationContext ctx) {
		UsuarioServicio serv = ctx.getBean(UsuarioServicio.class);
		UsuarioEntity usuario = new UsuarioEntity("Juan","Juan@gmail.com","123456","ROL_USUARIO",true,"default.jpg","BIO");
		
		serv.crearUsuario(usuario);
	}*/
	
	/**
	 * Metodo para crear contacto
	 */
	
	public static void crearContacto(ConfigurableApplicationContext ctx) {
		ContactoServicio serv = ctx.getBean(ContactoServicio.class);
		ContactoEntity contacto = new ContactoEntity("Maria", "Aguilar", "Ing. Sistemas", "mary@gmail.com", "1598198", "default.jpg", "Yo soy yo");
		
		serv.crearUsuario(contacto);
	}
	
	/**
	 * Metodo para actualizar usuario
	 */
	public static void actualizarUsuario(ConfigurableApplicationContext ctx) {
		UsuarioServicio serv = ctx.getBean(UsuarioServicio.class);
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setId((long) 1);
		usuario.setNombre("Julia");
		usuario.setEmail("jul@gmail.com");
		usuario.setPassword("654321");
		usuario.setBio("Juliana");
		usuario.setActivo(false);
		usuario.setImgUrl("sinimgae.jpg");
		
		serv.actualizar(usuario);
	
	}
	
	/**
	 * Metodo para eliminar
	 */
	
	public static void eliminarUsuario(ConfigurableApplicationContext ctx) {
		UsuarioServicio serv =  ctx.getBean(UsuarioServicio.class);
		serv.borrarPorId((long) 1);
	}
}
