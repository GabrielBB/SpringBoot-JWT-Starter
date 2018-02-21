/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.pce.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author Nestor Alduey
 */
@Entity
@Table(name = "ENTRY")
public class Entry implements Serializable {

	private static final long serialVersionUID = -446791582390940722L;
	@Id
    @Basic(optional = false) 
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Entry")
	@TableGenerator(name = "Entry", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="ENTRY")  
    private Integer id;
    
    private String value;
    
    private String decodedvalue;
    
    @ManyToOne        
    private Domain domain;

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public Entry() {
    }

    public Entry(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDecodedvalue() {
        return decodedvalue;
    }

    public void setDecodedvalue(String decodedvalue) {
        this.decodedvalue = decodedvalue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry other = (Entry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return decodedvalue;
    }

}
