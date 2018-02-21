package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
@NamedQuery(name="GSECFlujos.getAll", query="Select a from GSECFlujos a")
public class GSECFlujos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 253442289051792003L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator="GSECFlujos")
	@TableGenerator(name = "GSECFlujos", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="GSECFlujos")		
	private Long id;
	
	@ManyToOne
	private GSEC gsec;
	
	@OneToOne
	private Action action;
	
	@OneToOne
	private SubType subType;
		
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public SubType getSubType() {
		return subType;
	}

	public void setSubType(SubType subType) {
		this.subType = subType;
	}
	
	/*@EmbeddedId()
		private CombinacionActionSubType actionSubType;
	 */	
	private String flujo;

	

	public String getFlujo() {
		return flujo;
	}

	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GSEC getGsec() {
		return gsec;
	}

	public void setGsec(GSEC gsec) {
		this.gsec = gsec;
	}

	/*private void setActionSubType(CombinacionActionSubType actionSubType) {
		this.actionSubType = actionSubType;
	}

	private CombinacionActionSubType getActionSubType() {
		return actionSubType;
	}*/
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (subType != null ? subType.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof GSECFlujos)) {
			return false;
		}
		GSECFlujos other = (GSECFlujos) object;
		if ((this.subType == null && other.subType != null)
				|| (this.subType != null && !this.subType.equals(other.subType))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return action.getNombre()+" - "+ subType.getNombre() + " - "+ flujo;
	}
	
}
