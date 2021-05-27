package com.admincontactos.servicio;

import org.springframework.stereotype.Service;

import com.admincontactos.modelo.Mensaje;
import com.admincontactos.modelo.UsuarioEntity;

@Service
public class ErrorServicio {

	public ErrorServicio() {
		
	}
	
	public Mensaje validarRegistro(UsuarioEntity usuario) {
		if("".equalsIgnoreCase(usuario.getNombre())){
			return new Mensaje("E001","El Nombre no puede estar vacío");
		}if("".equalsIgnoreCase(usuario.getEmail())){
			return new Mensaje("E002","El Email no puede estar vacío");
		}if("".equalsIgnoreCase(usuario.getPassword())){
			return new Mensaje("E003","El Password no puede estar vacío");
		}if("".equalsIgnoreCase(usuario.getBio())){
			return new Mensaje("E004","Tu descripcion no puede estar vacío");
		}
		return new Mensaje("E005", "Hola " +usuario.getNombre() + " tu usuario ha sido registrado con Exito");
	}
}
