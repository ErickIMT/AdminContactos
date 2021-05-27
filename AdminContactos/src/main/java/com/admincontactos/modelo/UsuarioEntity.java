package com.admincontactos.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuario")
public class UsuarioEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(min =2 , max=50, message ="Minimo 2 caracteres maximo 50")
	private String nombre;

	@Column(unique = true)
	private String email;
	private String password;
	//private String rol;
	private boolean activo;
	private String imgUrl;
	@Column(length = 500)
	private String bio;
	
	@OneToMany(mappedBy ="usuario", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ContactoEntity>contactos = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="usuario_roles",joinColumns = @JoinColumn(name="usuario_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles=new HashSet<>();
	
	public UsuarioEntity() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<ContactoEntity> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactoEntity> contactos) {
		this.contactos = contactos;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	// NUEVO METODO
	public void addRole (Role role)
	{

		this.roles.add(role);
	}



	@Override
	public String toString() {
		return "UsuarioEntity{" +
				"nombre='" + nombre + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +

				", activo=" + activo +
				", imgUrl='" + imgUrl + '\'' +
				", bio='" + bio + '\'' +
				", contactos=" + contactos +
				", roles=" + roles +
				'}';
	}
}
