package com.admincontactos.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "contacto")
public class ContactoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	private String apellidos;
	private String trabajo;
	@Email(message="El email ingresado ya ha sido Registrado")
	@NotBlank(message="Columna Email es Requerida !! ")
	@Column(name = "correo", nullable = false,unique = true)
	private String correo;
	private String telefono;
	private String imagen;
	@Column(length = 5000)
	private String descripcion;
	
	@ManyToOne
	@JsonIgnore
	private UsuarioEntity usuario;
	
	public ContactoEntity() {
		
	}
	
	public ContactoEntity(String nombre, String apellidos, String trabajo, String correo, String telefono,
			String imagen, String descripcion) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.trabajo = trabajo;
		this.correo = correo;
		this.telefono = telefono;		
		this.imagen = imagen;
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTrabajo() {
		return trabajo;
	}
	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public UsuarioEntity getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}
	
	public boolean equals(Object obj) {
		return this.id == ((ContactoEntity)obj).getId();
	}

}
