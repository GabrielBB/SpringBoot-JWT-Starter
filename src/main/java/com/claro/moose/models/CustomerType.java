/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author Nestor Alduey
 */

@Entity
@NamedQueries({
    @NamedQuery(name="Customertype.findCode", query="select c from CustomerType c where c.code = :code"),
    @NamedQuery(name = "Customertype.findAll", query = "SELECT c FROM CustomerType c")
})

public class CustomerType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5977526051074865867L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Basic(optional = false)
	private Character code;
	@Basic(optional = false)
	private String name;

	public CustomerType() {
	}

	public CustomerType(Character code) {
		this.code = code;
	}

	public CustomerType(Character code, String name) {
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Character getCode() {
		return code;
	}

	public void setCode(Character code) {
		this.code = code;
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
		hash += (code != null ? code.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof CustomerType)) {
			return false;
		}
		CustomerType other = (CustomerType) object;
		if ((this.code == null && other.code != null)
				|| (this.code != null && !this.code.equals(other.code))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
