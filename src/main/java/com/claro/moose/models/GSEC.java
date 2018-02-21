package com.claro.moose.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
    @NamedQuery(name="GSEC.findName", query="Select g from GSEC g where g.name = :name"),
    @NamedQuery(name="GSEC.All", query="SELECT c FROM GSEC c")
})

public class GSEC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2861552811533821906L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="GSEC")
	@TableGenerator(name = "GSEC", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="GSEC")	
	private Long id;
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy="gsec",  cascade = CascadeType.ALL)	
	@Fetch(FetchMode.SUBSELECT)
	private List<GSECFlujos> flujos;

	public List<GSECFlujos> getFlujos() {
		return flujos;
	}

	public void setFlujos(List<GSECFlujos> flujos) {
		this.flujos = flujos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return name;
	}

	public void setNombre(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof GSEC)) {
			return false;
		}
		GSEC other = (GSEC) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	public String toString() {
		return name;
	}

}
