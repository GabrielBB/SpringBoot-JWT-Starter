/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@NoArgsConstructor
@Getter
@Setter
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
    
    private String decodedValue;
    
    @ManyToOne        
    private Domain domain;

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
        return decodedValue;
    }

}
