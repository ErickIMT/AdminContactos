package com.admincontactos.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admincontactos.modelo.ContactoEntity;
import com.admincontactos.repositorio.ContactoRepositorio;

@Service
public class ContactoServicio {

	@Autowired
	private ContactoRepositorio repo;
	
	public ContactoServicio() {
		
	}
	
	public List<ContactoEntity> buscarTodo(){
		return repo.findAll();
	}
	
	public ContactoEntity crearUsuario(ContactoEntity contacto) {
		return repo.save(contacto);
	}
	
	public ContactoEntity actualizar(ContactoEntity contacto) {
		
		ContactoEntity contactoActual = repo.findById(contacto.getId()).get();
		ContactoEntity contactoModif = null;
		
		if(contactoActual != null) {
			contactoActual.setNombre(contacto.getNombre());
			contactoActual.setApellidos(contacto.getApellidos());
			contactoActual.setTrabajo(contacto.getTrabajo());	
			contactoActual.setCorreo(contacto.getCorreo());	
			contactoActual.setTelefono(contacto.getTelefono());	
			contactoActual.setImagen(contacto.getImagen());	
			contactoActual.setDescripcion(contacto.getDescripcion());	
			contactoModif = repo.save(contactoActual);
		}
		
		return contactoModif;
	}
	
	public ContactoEntity buscarPorId(Long id) {
		
		return this.repo.findById(id).get();
	}
	
	public void borrarPorId(Long id) {
		
		this.repo.deleteById(id);
	}
	
}
