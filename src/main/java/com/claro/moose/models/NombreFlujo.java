package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="NombreFlujo.All", query="SELECT c FROM NombreFlujo c")
public class NombreFlujo implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5691500975696274812L;
	@Id
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return nombre;
	}
	
}
