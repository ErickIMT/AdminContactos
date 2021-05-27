package com.admincontactos.controlador;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.admincontactos.modelo.ContactoEntity;
import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.modelo.Mensaje;
import com.admincontactos.repositorio.ContactoRepositorio;
import com.admincontactos.repositorio.UsuarioRepositorio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private BCryptPasswordEncoder encodPass;
	@Autowired
	private UsuarioRepositorio repoUs;
	@Autowired
	private ContactoRepositorio repoCon;
	
	@ModelAttribute
	public void aggDatos(Model model, Principal principal) {
		String nombreUsuario = principal.getName();
		System.out.println("Login usuario: "+ nombreUsuario);
		
		//Obtener el usuario por el login de Usuario(Email)
		UsuarioEntity usuario = repoUs.getUsuarioPorEmail(nombreUsuario);
		System.out.println("Usuario: " + usuario);
		
		model.addAttribute("usuario", usuario);
	}
	
	@RequestMapping("/index")
	public String inicioTableroUsuario(Model model, Principal principal) {
		
		
		model.addAttribute("titulo","Tablero de Usuario");
		model.addAttribute("fragmento","/usuario/index");
		return "layout/layoutUsuarioTablero";
	}
	
	@GetMapping("/aggContacto")
	public String abrirAggContacto(Model model) {
		model.addAttribute("titulo", "Agregar Nuevo Contacto");
		model.addAttribute("contacto", new ContactoEntity());
		model.addAttribute("fragmento", "/usuario/aggContactoForm");
		return "layout/layoutUsuarioTablero";
	}
	
	//Procesar formulario de Agregar Contacto
	@RequestMapping(value ="/guardarContacto", method = RequestMethod.POST)
	public String guardarContacto(@ModelAttribute ContactoEntity contacto, /*@RequestParam("imagen") MultipartFile archivo,*/ Principal principal,
			HttpSession session){
		try {	
			
		String nombre = principal.getName();
		UsuarioEntity usuario = this.repoUs.getUsuarioPorEmail(nombre);
		contacto.setImagen("default.png");
		//Procesar y guardar archivo de Imagen
		/*
		if(archivo.isEmpty()) {
			System.out.println("Imagen vacia");			
			
		}else {
			
			contacto.setImagen(archivo.getOriginalFilename());
			
			//Guardar archivo en la carpeta
			File saveFile = new ClassPathResource("static/img/contactos").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+archivo.getOriginalFilename());
			
			Files.copy(archivo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("Imagen Cargada");
			
		}
		*/
		//Agregar el contacto
		usuario.getContactos().add(contacto);
		
		//Asignar a Id Usuario
		contacto.setUsuario(usuario);		
		
		//Guardar
		this.repoUs.save(usuario);
		
		System.out.println("Datos: " + contacto);
		System.out.println("Agregado a la base de Datos");
		
		//Mensaje Exitoso
		session.setAttribute("mensaje", new Mensaje("El contacto fue agregado!","success"));
		}catch(Exception e) {
			System.out.println("Error: "+ e.getMessage());
			e.printStackTrace();
			//Mensaje de Error
			session.setAttribute("mensaje", new Mensaje("Hubo algun error!! Intenta de nuevo","danger"));
		}
		return "redirect:/usuario/aggContacto";
	}
	
	//Consultar Contactos de Usuario
	//Paginacion por 5
	@GetMapping("/mostrar-contactos/{pag}")
	public String mostrarContactos(@PathVariable("pag") Integer pag, Model model, Principal principal) {
		//Listar contactos		
		
		String nombreUsuario = principal.getName();
		UsuarioEntity usuario = this.repoUs.getUsuarioPorEmail(nombreUsuario);
		
		//Pagina Actual = pag
		//Contactos por pagina = 5
		Pageable pageable = PageRequest.of(pag, 5);
		
		Page<ContactoEntity> contactos = this.repoCon.buscarContactosXUsuario(usuario.getId(),pageable);
		
		model.addAttribute("contactos", contactos);
		model.addAttribute("currentPage",pag);		
		model.addAttribute("totalPags",contactos.getTotalPages());
		
		model.addAttribute("titulo", "Lista de contactos");
		model.addAttribute("fragmento", "/usuario/mostrar-contactos");
		return "layout/layoutUsuarioTablero";
	}
	
	//Mostrar Detalles de un Contacto
	@RequestMapping("/{id}/contacto")
	public String mostrarDetalleContacto(@PathVariable("id") Long id, Model model, Principal principal) {
		
		Optional<ContactoEntity> contactoOpt = this.repoCon.findById(id);
		ContactoEntity contacto = contactoOpt.get();
		
		String nomUsuario = principal.getName();
		UsuarioEntity usuario = this.repoUs.getUsuarioPorEmail(nomUsuario);
		
		if(usuario.getId() == contacto.getUsuario().getId()) {
			model.addAttribute("contacto",contacto);
		}	
				
		model.addAttribute("titulo", contacto.getNombre()+" "+contacto.getApellidos());
		model.addAttribute("fragmento", "/usuario/detalle-contacto");
		return "layout/layoutUsuarioTablero";
	}
	
	//Borrar Contacto
	@GetMapping("/borrar/{id}")
	public String borrarContacto(@PathVariable("id") Long id, HttpSession session, Principal principal) {
		
		ContactoEntity contacto = this.repoCon.findById(id).get();
		
		UsuarioEntity usuario = this.repoUs.getUsuarioPorEmail(principal.getName());
		
		usuario.getContactos().remove(contacto);
		this.repoUs.save(usuario);
		
		session.setAttribute("mensaje", new Mensaje("Contacto borrado con exito!!","success"));
		return "redirect:/usuario/mostrar-contactos/0";
	}
	
	//Editar Contacto
	@PostMapping("/editar/{id}")
	public String editarContacto(@PathVariable("id")Long id, Model model) {
		
		ContactoEntity contacto = this.repoCon.findById(id).get();
		
		model.addAttribute("contacto",contacto);
		model.addAttribute("titulo", "Editar Contacto");
		model.addAttribute("fragmento", "/usuario/editar-contacto");
		return "layout/layoutUsuarioTablero";
	}
	
	//Procesar formulario de editar contacto
	@PostMapping("/guardarContactoEditado")
	public String guardarContactoEditado(@ModelAttribute ContactoEntity contacto,/* @RequestParam("imagen")MultipartFile file,*/ 
			Model model, HttpSession session, Principal principal) {
		
			System.out.println("Nombre: "+contacto.getNombre());
			System.out.println("ID: "+contacto.getId());
		try {
			
			//Detalles contacto Antiguo
			ContactoEntity contactoAnt = this.repoCon.findById(contacto.getId()).get();
			
			/*
			//imagen
			if(!file.isEmpty()) {
				
				//Borrar Imagen Antigua
				File deleteFile = new ClassPathResource("static/img/contactos").getFile();
				File file1 = new File(deleteFile, contactoAnt.getImagen());
				file1.delete();
				
				//Subir nueva Imagen
				File saveFile = new ClassPathResource("static/img/contactos").getFile();
				
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				contacto.setImagen(file.getOriginalFilename());
				
				
			}else {
				contacto.setImagen(contactoAnt.getImagen());
			}*/
			contacto.setImagen(contactoAnt.getImagen());
			UsuarioEntity usuario = this.repoUs.getUsuarioPorEmail(principal.getName());
			contacto.setUsuario(usuario);
			this.repoCon.save(contacto);
			
			session.setAttribute("mensaje", new Mensaje("Tu contacto ha si actualizado","success"));
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		model.addAttribute("titulo", "Editar Contacto");
		model.addAttribute("fragmento", "/usuario/editar-contacto");
		return "redirect:/usuario/"+contacto.getId()+"/contacto";
	}
	
	//Perfil de Usuario
	
	@GetMapping("/perfil")
	public String perfilUsuario(Model model) {
		
		
		
		model.addAttribute("titulo", "Mi Perfil");
		model.addAttribute("fragmento", "/usuario/perfil");
		return "layout/layoutUsuarioTablero";
	}
	
	//Settings Usuario
	
	@GetMapping("/settings")
	public String openSettings(Model model) {
		
		
		model.addAttribute("titulo", "Mi Configuracion");
		model.addAttribute("fragmento", "/usuario/settings");
		return "layout/layoutUsuarioTablero";
	}
	
	//Cambiar Password	
	@PostMapping("/cambiarPass")
	public String changePassword(@RequestParam("PassAntiguo") String passAntiguo, @RequestParam("PassNuevo") String passNuevo, 
			Principal principal, HttpSession session) {
		
		System.out.println("Password antiguo: " +passAntiguo);
		System.out.println("Password nuevo: " +passNuevo);
		
		String emailUsuario= principal.getName();
		UsuarioEntity usuario = this.repoUs.getUsuarioPorEmail(emailUsuario);
		
		//Cambiar Password
		if(this.encodPass.matches(passAntiguo, usuario.getPassword())) {
			usuario.setPassword(this.encodPass.encode(passNuevo));
			this.repoUs.save(usuario);
			session.setAttribute("mensaje", new Mensaje("El Password ha sido cambiado con exito","alert alert-success"));
			
		}else {
			session.setAttribute("mensaje", new Mensaje("El password no es correcto","alert alert-danger"));
			return "redirect:/usuario/settings";
		}
		
		return "redirect:/usuario/index";
	}
	
}
