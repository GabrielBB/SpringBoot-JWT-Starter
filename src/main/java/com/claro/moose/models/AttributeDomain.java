package com.claro.moose.pce.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQuery(name="AttributeDomain.buscarAtributoDomino", query = "select o from AttributeDomain o where o.attribute = :atributo and o.domain = :dominio and o.component = :componente")
public class AttributeDomain implements Serializable {
	private static final long serialVersionUID = 3735392648402940252L;

	@Id
    @Basic(optional = false) 
	@GeneratedValue(strategy=GenerationType.TABLE, generator="AttributeDomain")
    @TableGenerator(name = "AttributeDomain", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="ATTRIBUTE_DOMAIN")	
	private Long id;
	@OneToOne
	private Attribute attribute;
	@OneToOne
	private Domain domain;	
	@ManyToOne
	private Component component;	
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Entry> oldEntries;
	
	public AttributeDomain() {
		oldEntries = new ArrayList<Entry>();
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public Domain getDomain() {
		return domain;
	}
	
	public List<Entry> getAllEntries() {
		List<Entry> entries = getDomain().getEntry();
		entries.addAll(oldEntries);
		
		return entries;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != 0  && id != null? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttributeDomain)) {
            return false;
        }
        AttributeDomain other = (AttributeDomain) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return attribute.getName();
    }
	public void setComponent(Component component) {
		this.component = component;
	}
	public Component getComponent() {
		return component;
	}
	public void setOldEntries(List<Entry> oldEntries) {
		this.oldEntries = oldEntries;
	}
	public List<Entry> getOldEntries() {
		return oldEntries;
	}
}
