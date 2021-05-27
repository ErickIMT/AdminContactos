package com.admincontactos.repositorio;

import com.admincontactos.modelo.ContactoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.admincontactos.modelo.UsuarioEntity;


public interface UsuarioRepositorio extends JpaRepository<UsuarioEntity, Long> {

	@Query("select u from UsuarioEntity u where u.email = :email")
	public UsuarioEntity getUsuarioPorEmail(@Param("email")String email);


	@Query("SELECT u FROM UsuarioEntity u")
	public Page<UsuarioEntity> listarUsuarios( Pageable pag);



	/*@Query("from ContactoEntity as c where c.usuario.idimgUrl = :id")
	public Page<ContactoEntity> buscarContactosXUsuario(@Param("id") Long id, Pageable pag);*/
}
