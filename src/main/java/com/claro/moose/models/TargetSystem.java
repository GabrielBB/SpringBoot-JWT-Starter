/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.pce.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Nestor Alduey
 */
@Entity
@Table(name = "TARGETSYSTEM")
public class TargetSystem implements Serializable {

	private static final long serialVersionUID = -4155271894699505412L;

	@Id
	private String name;

	public TargetSystem() {
	}

	public TargetSystem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (name != null ? name.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TargetSystem)) {
			return false;
		}
		TargetSystem other = (TargetSystem) object;
		if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
