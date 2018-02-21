package com.claro.moose.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;


@Entity
@NamedQueries({
    @NamedQuery(name = "ACTION.All", query = "SELECT c FROM Action c"),
    @NamedQuery(name = "Action.findName", query = "Select a from Action a where a.nombre =:nombre")
})

public class Action implements Serializable {

	private static final long serialVersionUID = -2286413058844849397L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="Action")
    @TableGenerator(name = "Action", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="Action")	
	private Long id;

	private String nombre;

	@OneToMany(mappedBy = "action")
	private List<SubType> subType;
	
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
		if (!(object instanceof Action)) {
			return false;
		}
		Action other = (Action) object;
		if ((this.nombre == null && other.nombre != null)
				|| (this.nombre != null && !this.nombre.equals(other.nombre))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "" + nombre;
	}
	
	public Action() {
		setSubType(new ArrayList<SubType>());
	}

	private void setSubType(List<SubType> subType) {
		this.subType = subType;
	}

	private List<SubType> getSubType() {
		return subType;
	}
}
