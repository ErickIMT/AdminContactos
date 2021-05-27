package com.admincontactos.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.admincontactos.modelo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.admincontactos.modelo.UsuarioEntity;

public class UsuarioDetalles implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UsuarioEntity usuario;	
	
	public UsuarioDetalles(UsuarioEntity usuario) {
		super();
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<Role> roles=usuario.getRoles();
		List<SimpleGrantedAuthority> authorities=new ArrayList<>();

		for (Role role:roles){
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
	
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {

		return usuario.isActivo();
	}

}
