package com.admincontactos.servicio;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendEmail(String subject, String message, String to) {
		
		boolean f = false;
		
		String from = "erickisrael203@gmail.com";
		
		//Variable para Gmail
		String host ="smtp.gmail.com";
		
		//Obtener propiedades el sistema
		Properties properties = System.getProperties();
		System.out.println("Propiedades: " + properties);
		
		//Paso 1: Configuracion de informacion importante de las propiedade
		
		//Host Set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", true);
		properties.put("mail.smtp.auth", true);
		
		//Obtener la session
		
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("erickisrael203@gmail.com", "8620152erick");
			}			
		});
		
		session.setDebug(true);
		
		//Paso 2: Composicion del mensaje
		
		MimeMessage m = new MimeMessage(session);
		
		try {
			//from email
			m.setFrom(from);
			
			//agregar recipiente de mensaje
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//agregar titulo de mensaje
			m.setSubject(subject);
			
			//agregar texto del mensaje
			//m.setText(message);
			m.setContent(message, "text/html");
			
			//Enviar
			
		//Paso 3: Enviar el mensaje usando la clase Transport
			Transport.send(m);
			
			System.out.println("Envio exitoso!!...");
			f = true;			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return f;
		
	}
}
