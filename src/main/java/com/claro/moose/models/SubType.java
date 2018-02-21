package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

@Entity
@NamedQueries({
    @NamedQuery(name = "SubType.OfAction", query = "SELECT c FROM SubType c WHERE c.action = :action"),
    @NamedQuery(name = "SubType.findName", query = "SELECT c FROM SubType c WHERE c.nombre = :nombre")
})
public class SubType implements Serializable{
	
	private static final long serialVersionUID = 5621383389717458333L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="SubType")
	@TableGenerator(name="SubType", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		 initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="SubType")	
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne
	private Action action;
	
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
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
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (nombre != null ? nombre.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SubType)) {
			return false;
		}
		SubType other = (SubType) object;
		if ((this.nombre == null && other.nombre != null)
				|| (this.nombre != null && !this.nombre.equals(other.nombre))) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return descripcion!=null?descripcion:nombre;
	}
}
