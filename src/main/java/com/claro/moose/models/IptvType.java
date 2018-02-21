/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author Nestor Alduey
 */
@Entity
@NamedQuery(name="IptvType.All", query="SELECT o FROM IptvType o")
public class IptvType implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -9033860358913822033L;
	@Id
    @Basic(optional = false)    
    private String code;
    
    private String name;
    
    

    public IptvType() {
    }

    public IptvType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IptvType)) {
            return false;
        }
        IptvType other = (IptvType) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
