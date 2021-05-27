package com.admincontactos.controlador;

import com.admincontactos.modelo.Role;
import com.admincontactos.repositorio.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.admincontactos.modelo.Mensaje;
import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.servicio.UsuarioServicio;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioServicio servicioUsuario;

	@Autowired
	private RoleRepository role_repo;
		
	@RequestMapping("/home")
	public String home(Model model) {
		
		model.addAttribute("titulo", "Home - Administrador de Contactos");
		model.addAttribute("fragmento","home.html");
		return "layout/layoutHome";
	}
	
	@RequestMapping("/about")
	public String acercaDe(Model model) {
		
		model.addAttribute("titulo","Acerca De Nosotros - Administrador de Contactos");
		model.addAttribute("fragmento","acercaDe.html");
		return "layout/layoutHome";
	}
	
	@RequestMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("titulo","Registrate - Administrador de Contactos");
		model.addAttribute("fragmento","registrar.html");
		UsuarioEntity usuario = new UsuarioEntity();
		model.addAttribute("usuario",usuario);
		return "layout/layoutHome";
	}
	
	@RequestMapping(value="/registrar", method = RequestMethod.POST)
	public ModelAndView registrarUsuario(@ModelAttribute("usuario") UsuarioEntity usuario) {
		
		ModelAndView mav = null;
		
		try {
			Role roleuser=role_repo.findByName("ROLE_USER");
			usuario.addRole(roleuser);
			//usuario.setRol("ROLE_USER");
			usuario.setActivo(true);
			usuario.setImgUrl("default.png");
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			servicioUsuario.crearUsuario(usuario);
			mav = new ModelAndView("/layout/layoutHome");
			mav.addObject("mensaje", new Mensaje("El Usuario fue registrado exitosamente!!", "success"));
			mav.addObject("fragmento","/registrar");
			
		} catch (Exception e) {
			e.printStackTrace();
			
			// TODO: handle exception
			mav = new ModelAndView("/layout/layoutHome");
			mav.addObject("mensaje", new Mensaje("Algo salio Mal !! "+e.getMessage(),"alert-danger"));
			mav.addObject("fragmento","/registrar");
		} 
		
		return mav;
	}
	
	@GetMapping("/ingresar")
	public String loginUsuario(Model model) {
		model.addAttribute("titulo","Login - Administrador de Contactos");
		return "/login";
	}

}
