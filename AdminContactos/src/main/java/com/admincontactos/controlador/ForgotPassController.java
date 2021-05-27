package com.admincontactos.controlador;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.admincontactos.modelo.UsuarioEntity;
import com.admincontactos.repositorio.UsuarioRepositorio;
import com.admincontactos.servicio.EmailService;

@Controller
public class ForgotPassController {

	Random random = new Random();
	
	@Autowired
	private EmailService emailServ;
	
	@Autowired
	private UsuarioRepositorio repous;
	
	@Autowired
	private BCryptPasswordEncoder encodePass;
	
	@RequestMapping("/forgotPass")
	public String abrirEmailForm(Model model, HttpSession session) {
		
		model.addAttribute("titulo","Olvide Mi Password - Administrador de Contactos");
		model.addAttribute("fragmento","forgotPass");
		return "layout/layoutHome";
	}
	
	@PostMapping("/sendMailOtp")
	public String sendMailOtp(@RequestParam("email") String email, HttpSession session) {
		
		System.out.println("Correo: "+email);
		
		//Generar Clave aleatoria de 6  digitos
		
		int otp = random.nextInt(999999);
		
		String subject="Clave OTP del Administrador de Contactos";
		String message="La clave OTP es= "+otp;
		String to = email;
		
		System.out.println("OTP: "+otp);
		boolean resp = this.emailServ.sendEmail(subject, message, to);
		
		if(resp) {
			
			session.setAttribute("otp", otp);
			session.setAttribute("email", email);
			return "redirect:/verificarOtp";
		}else {
			session.setAttribute("mensaje", "Verifica que el correo este correcto");
			return "redirect:/forgotPass";
		}		
	}
	
	@RequestMapping("/verificarOtp")
	public String verificarOtp(Model model) {
		
		model.addAttribute("titulo", "Clave OTP");
		model.addAttribute("fragmento","verificarOtp.html");
		return "layout/layoutHome";
	}
	
	//Verificar Numero OTP
	
	@PostMapping("/verificarOtpForm")
	public String verificarOtpForm(@RequestParam("otp")int otp, HttpSession session) {
		
		int vOtp = (int) session.getAttribute("otp");
		String email = (String) session.getAttribute("email");
		
		if(vOtp == otp) {
			
			UsuarioEntity usuario = this.repous.getUsuarioPorEmail(email);
			
			if(usuario == null) {
				session.setAttribute("mensajeOtp", "No existe usuario con este correo!!");
				
				return "redirect:/forgotPass";
			}else {
				//Reenvia al formulario de cambiar Password
				return "redirect:/cambiarPass";
			}
			
		}else {
			
			session.setAttribute("mensajeOtp", "Haz ingresado un codigo incorrecto!!");
			return "redirect:/verificarOtp";
		}				
	}
	
	@RequestMapping("/cambiarPass")
	public String cambiarPassForm(Model model) {
		
		model.addAttribute("titulo", "Cambiar Password");
		model.addAttribute("fragmento","cambiarPass");
		return "layout/layoutHome";
	}
	
	@PostMapping("/cambiarPass")
	public String cambiarPass(@RequestParam("newPass") String newPass, HttpSession session) {
		String email = (String) session.getAttribute("email");
		UsuarioEntity usuario = this.repous.getUsuarioPorEmail(email);
		usuario.setPassword(this.encodePass.encode(newPass));
		this.repous.save(usuario);		
		session.setAttribute("mensajeOtp", "");
		return "redirect:/ingresar?change=Password cambiado con exito..!";
	}
}
