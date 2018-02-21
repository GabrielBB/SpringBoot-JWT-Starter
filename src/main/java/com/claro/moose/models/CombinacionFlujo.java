package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class CombinacionFlujo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7099479551841732394L;

	@OneToOne
	private Action accion;
	
	@OneToOne
	private SubType subtipo;
	
	private String flujo;

	public String getFlujos() {
		return flujo;
	}

	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}

	public Action getAccion() {
		return accion;
	}

	public void setAccion(Action accion) {
		this.accion = accion;
	}

	public SubType getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(SubType subtipo) {
		this.subtipo = subtipo;
	}

}
