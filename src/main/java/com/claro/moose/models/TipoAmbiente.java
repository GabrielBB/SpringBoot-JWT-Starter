package com.claro.moose.pce.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
@Entity
@NamedQuery(name="GetAllTipoAmb", query="SELECT o FROM TipoAmbiente o")
public class TipoAmbiente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2098021060859142967L;
	@Id
	private String value;
	private String label;
	
	public TipoAmbiente() {
		
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public TipoAmbiente(String value, String label) {
		super();
		this.value = value;
		this.label = label;
	}
	@Override
	public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAmbiente)) {
            return false;
        }
        TipoAmbiente other = (TipoAmbiente) object;
        if ((this.value == "" && other.value != "") || (this.value != "" && !(this.value == other.value))) {
            return false;
        }
        return true;
    }
	@Override
	public int hashCode() {
		int hash = 0;
        hash += (value != null ? value.hashCode() : 0);
        
        return hash;
	}
	@Override
	public String toString() {
		return label;
	}
	
	
	
}
