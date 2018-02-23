package com.claro.moose.models;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ATTRIBUTEDOMAIN", schema = "MOOSE")
@Data
@NoArgsConstructor
public class AttributeDomain  {

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
	@ManyToOne(fetch = FetchType.LAZY)
	private Component component;	

	
/*	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null && id != 0 ? id.hashCode() : 0);
        return hash;
    }*/

    @Override
    public boolean equals(Object object) {
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
}
