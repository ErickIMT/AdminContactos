package com.admincontactos.modelo;

import java.io.Serializable;

public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	private String tipo;
	
	public Mensaje(String mensaje, String tipo) {
		super();
		this.mensaje = mensaje;
		this.tipo = tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	
}
