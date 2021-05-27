package com.admincontactos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.admincontactos.config.UsuarioDetalles;
import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.repositorio.UsuarioRepositorio;

@Service
public class UsuarioDetallesServicio implements UserDetailsService{

	@Autowired
	private UsuarioRepositorio repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//Recuperar Usuario de la BD
		UsuarioEntity usuario = repo.getUsuarioPorEmail(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("No se pudo encontrar el Usuario");
		}
		
		UsuarioDetalles usuarioDetalles = new UsuarioDetalles(usuario);
		
		return usuarioDetalles;
	}

}
