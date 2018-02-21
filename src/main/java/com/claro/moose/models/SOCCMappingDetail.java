package com.claro.moose.models;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
public class SOCCMappingDetail implements Serializable {

	/**
  * 
  */
	private static final long serialVersionUID = -8261111717214905845L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="SOCCMappingDetail")
	@TableGenerator(name="SOCCMappingDetail", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		 initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="SOCCMappingDetail")  	
	private Long id;

	@OneToOne
	private Component oferta;

	private Long socc;

	private boolean isMultiplan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Component getOferta() {
		return oferta;
	}

	public void setOferta(Component oferta) {
		this.oferta = oferta;
	}

	public Long getSocc() {
		return socc;
	}

	public void setSocc(Long socc) {
		this.socc = socc;
	}

	public boolean isMultiplan() {
		return isMultiplan;
	}

	public void setMultiplan(boolean isMultiplan) {
		this.isMultiplan = isMultiplan;
	}

	public SOCCMapping getSoccMapping() {
		return soccMapping;
	}

	public void setSoccMapping(SOCCMapping soccMapping) {
		this.soccMapping = soccMapping;
	}

	@ManyToOne
	private SOCCMapping soccMapping;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (oferta != null ? oferta.getId().hashCode() : 0);
		return hash;
	}

	
	public void setSOCCMappingDetail(SOCCMappingDetail socmapdet) {
		setSOCCMappingDetail(socmapdet. id, socmapdet. oferta, socmapdet. socc,
				socmapdet. isMultiplan, socmapdet. soccMapping);	
	}
	public void setSOCCMappingDetail(Long id, Component oferta, Long socc,
			boolean isMultiplan, SOCCMapping soccMapping) {		
		this.id = null;
		this.oferta = oferta;
		this.socc = socc;
		this.isMultiplan = isMultiplan;
		this.soccMapping = null;
	}

	@Override
	public boolean equals(Object object) {	
		if (!(object instanceof SOCCMappingDetail)) {
			return false;
		}
		SOCCMappingDetail other = (SOCCMappingDetail) object;
		if (((this.oferta == null || this.oferta.getId() == null) && (other.oferta != null || other.oferta.getId() !=null))
				|| (this.oferta.getId() != null && !this.oferta.getId().equals(other.oferta.getId()))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		if (soccMapping != null && soccMapping.getComponent() != null)
			name.append(soccMapping.getComponent().getName()+ " - ");			
		if (oferta != null)
			name.append(oferta.getName());
		if (socc != null)
			name.append("-" + socc);
		name.append("-" + (isMultiplan ? "Si" : "No"));
		return name.toString();
	}

}