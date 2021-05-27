package com.admincontactos.controlador;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.admincontactos.modelo.ContactoEntity;
import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.repositorio.ContactoRepositorio;
import com.admincontactos.repositorio.UsuarioRepositorio;

@RestController
public class SearchController {
	
	@Autowired
	private UsuarioRepositorio RepoUs;
	
	@Autowired
	private ContactoRepositorio RepoCon;
	
	//Busqueda de contactos
	@GetMapping("/buscar/{query}")
	public ResponseEntity<?> buscarContacto(@PathVariable("query")String query, Principal principal){
		
		UsuarioEntity usuario = this.RepoUs.getUsuarioPorEmail(principal.getName());
		
		List<ContactoEntity> contactosResult = this.RepoCon.findByNombreContainingAndUsuario(query, usuario);
		List<ContactoEntity> contactosResultApe = this.RepoCon.findByApellidosContainingAndUsuario(query, usuario);
		contactosResult.addAll(contactosResultApe);
		return ResponseEntity.ok(contactosResult);
	}

}
