/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.models;

import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NoArgsConstructor
@Data
@Table(name = "DOMAIN", schema = "MOOSE")
@NamedQueries({
		@NamedQuery(name = "Domain.getDomain", query = "SELECT o FROM Domain o WHERE o.name = :name"),
		@NamedQuery(name = "findDomain", query = "select d from Domain d where d.name = :name"),
		@NamedQuery(name = "getAllDomains", query = "select d from Domain d")		
})
public class Domain  {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="Domain")
	@TableGenerator(name = "Domain", table="HIBERNATE_SEQUENCES", allocationSize=1, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="DOMAIN")
	private Integer id;
	@Basic
	private String name;

	@OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Entry> entry;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Domain)) {
			return false;
		}
		Domain other = (Domain) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}
