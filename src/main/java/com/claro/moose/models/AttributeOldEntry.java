package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
public class AttributeOldEntry implements Serializable {
	private static final long serialVersionUID = -54466508355438991L;
	@Id
    @Basic(optional = false) 
	@GeneratedValue(strategy=GenerationType.TABLE, generator="AttributeOldEntry")
    @TableGenerator(name = "AttributeOldEntry", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="ATTRIBUTE_OLD_ENTRY")	
	private Long id;
	@OneToOne
	private Component component;
	@OneToOne
	private Attribute attribute;
	@OneToOne
	private Entry entry;
		
	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != 0 ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attribute)) {
            return false;
        }
        AttributeOldEntry other = (AttributeOldEntry) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
