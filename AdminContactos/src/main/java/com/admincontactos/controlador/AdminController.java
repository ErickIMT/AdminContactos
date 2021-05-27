package com.admincontactos.controlador;

import com.admincontactos.modelo.Role;
import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.repositorio.UsuarioRepositorio;
import com.admincontactos.servicio.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller

@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UsuarioRepositorio repoUs;
    @Autowired
    private UsuarioServicio service;

    @ModelAttribute
	public void aggDatos(Model model, Principal principal) {
		String nombreUsuario = principal.getName();
		System.out.println("Login usuario: "+ nombreUsuario);
		
		//Obtener el usuario por el login de Usuario(Email)
		UsuarioEntity usuario = repoUs.getUsuarioPorEmail(nombreUsuario);
		
		model.addAttribute("usuario", usuario);
	}
    
    //Consultar Contactos de Usuario
    //Paginacion por 5
    @GetMapping("/admin-usuarios/{pag}")
    public String mostrarContactos(@PathVariable("pag") Integer pag, Model model, Principal principal) {
        //Listar contactos

        String nombreUsuario = principal.getName();
        UsuarioEntity admin = this.repoUs.getUsuarioPorEmail(nombreUsuario);

        //Pagina Actual = pag
        //Contactos por pagina = 5
        Pageable pageable = PageRequest.of(pag, 5);

        Page<UsuarioEntity> usuarios = this.repoUs.listarUsuarios(pageable);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("currentPage",pag);
        model.addAttribute("admin",admin);
        model.addAttribute("totalPags",usuarios.getTotalPages());

        model.addAttribute("titulo", "Lista de Usuarios");
        model.addAttribute("fragmento", "/usuario/admin-usuarios");
        return "layout/layoutUsuarioTablero";
    }
    @GetMapping("/edit/{id}")
    public  String editUser(@PathVariable("id") Long id,Model model){
        UsuarioEntity user=service.buscarPorId(id);
        List<Role> listRoles=service.gerRoles();
        
        model.addAttribute("titulo","Editar Usuario");
        model.addAttribute("usuario",user);
        model.addAttribute("listRoles",listRoles);
        model.addAttribute("fragmento","/admin/user_form");

        return "layout/layoutUsuarioTablero";

    }

    @PostMapping("/update/save")
    public String saveUser(UsuarioEntity user){
        try {
        	
            service.saveSinPassEnc(user);


        } catch (Exception e) {
            e.printStackTrace();


        }
            return "redirect:/admin/admin-usuarios/0";
    }
}
