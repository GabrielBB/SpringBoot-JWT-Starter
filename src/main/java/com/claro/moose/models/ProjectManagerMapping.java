package com.claro.moose.pce.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

@Entity
@NamedQuery(name="ProjectManagerMapping.All", query="SELECT o FROM ProjectManagerMapping o")
public class ProjectManagerMapping implements Serializable{

	
	private static final long serialVersionUID = -8237718441700241440L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="ProjectManagerMapping")
	@TableGenerator(name="ProjectManagerMapping", table="HIBERNATE_SEQUENCES", allocationSize=1, 
			initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="ProjectManagerMapping") 	
	private Long id;
	private Long code;
	private String name;
	private int parentSpec;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectManagerMapping() {
		
	}
	
	public void setPM(ProjectManagerMapping pm) {
		this.code = pm.getCode();
		this.name = pm.getName();
		this.parentSpec = pm.getParentSpec();
	}
	
	public int getParentSpec() {
		return parentSpec;
	}

	public void setParentSpec(int parentSpec) {
		this.parentSpec = parentSpec;
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
		if (!(object instanceof ProjectManagerMapping)) {
			return false;
		}
		ProjectManagerMapping other = (ProjectManagerMapping) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id
						.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name!=null?name:"";
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
