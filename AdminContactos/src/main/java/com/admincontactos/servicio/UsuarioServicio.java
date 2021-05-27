package com.admincontactos.servicio;

import java.util.List;

import com.admincontactos.modelo.Role;
import com.admincontactos.repositorio.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepositorio repo;

	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UsuarioServicio() {
		
	}
	
	public List<UsuarioEntity> buscarTodo(){
		return repo.findAll();
	}
	
	public UsuarioEntity crearUsuario(UsuarioEntity usuario) {
		return repo.save(usuario);
	}
	
	public UsuarioEntity actualizar(UsuarioEntity user) {
		
		UsuarioEntity userActual = repo.findById(user.getId()).get();
		UsuarioEntity usuarioModificado = null;
		
		if(userActual != null) {
			userActual.setNombre(user.getNombre());
			userActual.setEmail(user.getEmail());
			userActual.setPassword(user.getPassword());	
			userActual.setBio(user.getBio());	
			userActual.setImgUrl(user.getImgUrl());	
			usuarioModificado = repo.save(userActual);
		}
		
		return usuarioModificado;
	}
	
	public UsuarioEntity buscarPorId(Long id) {
		
		return this.repo.findById(id).get();
	}
	
	public void borrarPorId(Long id) {
		
		this.repo.deleteById(id);
	}

	public List<Role>gerRoles(){
		return roleRepo.findAll();
	}


	public void save(UsuarioEntity user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repo.save(user);

	}
	
	public void saveSinPassEnc(UsuarioEntity user) {
		repo.save(user);
	}


}
