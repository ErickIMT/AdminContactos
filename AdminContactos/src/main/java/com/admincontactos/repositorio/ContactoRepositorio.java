package com.admincontactos.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.admincontactos.modelo.ContactoEntity;
import com.admincontactos.modelo.UsuarioEntity;


public interface ContactoRepositorio extends JpaRepository<ContactoEntity, Long> {

	//Paginacion
	//Consulta de Contatos del usuario
	@Query("from ContactoEntity as c where c.usuario.id = :id")
	public Page<ContactoEntity> buscarContactosXUsuario(@Param("id") Long id, Pageable pag);
		
	//Busqueda	
	public List<ContactoEntity> findByNombreContainingAndUsuario(String nombre, UsuarioEntity usuario);
	
	public List<ContactoEntity> findByApellidosContainingAndUsuario(String nombre, UsuarioEntity usuario);
	
}
