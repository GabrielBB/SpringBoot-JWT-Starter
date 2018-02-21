package com.claro.moose.pce.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TableGenerator;

@Entity
@NamedQueries({
    @NamedQuery(name = "ADSLIPTVMapping.Mapping", query = "SELECT o FROM ADSLIPTVMapping o WHERE o.component = :component order by o.mappingVersion desc"),
    @NamedQuery(name = "ADSLIPTVMapping.findAll", query = "SELECT o FROM ADSLIPTVMapping o"),
    @NamedQuery(name = "ADSLIPTVMapping.GetMapping", query = "SELECT o FROM ADSLIPTVMapping o WHERE o.component = :component and o.banwitdth = :banwitdth and o.iptvType = :iptvType order by o.mappingVersion desc")
})
@NamedNativeQuery(name="ADSLIPTVMapping.getAllMaxVersion", query="select * from ADSLIPTVMAPPING m " +
		"  where (m.component_id || m.iptvtype_id || m.banwitdth_id) in ( select distinct (c.component_id || c.iptvtype_id || c.banwitdth_id) from AdslIPTVMAPPING c) " +
		"  and m.mappingversion = (select max(q.mappingversion) from adsliptvmapping q where (m.component_id || m.iptvtype_id || m.banwitdth_id) = (q.component_id || q.iptvtype_id || q.banwitdth_id)) " +
		"  ", resultSetMapping="AIMappingsSet")
@SqlResultSetMapping(name="AIMappingsSet", entities={
@EntityResult(entityClass= com.claro.moose.pce.domain.ADSLIPTVMapping.class, fields = {
@FieldResult(name="id", column="id"),
@FieldResult(name="component", column="component_id"),
@FieldResult(name="banwitdth", column="banwitdth_id"),
@FieldResult(name="iptvType", column="iptvType_id"),
@FieldResult(name="parentspec", column="parentspec"),
@FieldResult(name="childspec2", column="childspec2"),
@FieldResult(name="childspec3", column="childspec3"),
@FieldResult(name="childspec1", column="childspec1"),
@FieldResult(name="mappingVersion", column="mappingVersion"),
@FieldResult(name="pceVersion", column="pceVersion_id")
})})
public class ADSLIPTVMapping implements Serializable {

	private static final long serialVersionUID = -3488311381108828271L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="ADSLIPTVMapping")	
    @TableGenerator(name = "ADSLIPTVMapping", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="ADSLIPTVMapping")
	private Long id;
	
	@OneToOne
	private Component component;
	
	@OneToOne
	private Entry banwitdth;
	
	@OneToOne
	private Entry iptvType;
		
	private Integer parentspec;

	private Integer childspec1;

	private Integer childspec2;

	private Integer childspec3;
	
	private Long mappingVersion;
	
	@OneToOne
	private PCEVersion pceVersion;
	
	
	public Long getMappingVersion() {
		return mappingVersion;
	}

	public void setMappingVersion(Long mappingVersion) {
		this.mappingVersion = mappingVersion;
	}

	public PCEVersion getPceVersion() {
		return pceVersion;
	}

	public void setPceVersion(PCEVersion pceVersion) {
		this.pceVersion = pceVersion;
	}

	public void setADSLIPTVMapping(ADSLIPTVMapping adslIptvMapping) {
		this.banwitdth = adslIptvMapping.getBanwitdth();
		this.iptvType = adslIptvMapping.getIptvType();
		this.parentspec = adslIptvMapping.getParentspec();
		this.childspec1= adslIptvMapping.getChildspec1();
		this.childspec2 = adslIptvMapping.getChildspec2();
		this.childspec3 = adslIptvMapping.getChildspec3();
		this.component =  adslIptvMapping.getComponent();
		this.mappingVersion = adslIptvMapping.getMappingVersion();
		this.pceVersion =  adslIptvMapping.getPceVersion();
		this.id = null;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Entry getBanwitdth() {
		return banwitdth;
	}

	public void setBanwitdth(Entry banwitdth) {
		this.banwitdth = banwitdth;
	}

	public Entry getIptvType() {
		return iptvType;
	}

	public void setIptvType(Entry iptvType) {
		this.iptvType = iptvType;
	}

	public Integer getParentspec() {
		return parentspec;
	}

	public void setParentspec(Integer parentspec) {
		this.parentspec = parentspec;
	}

	public Integer getChildspec1() {
		return childspec1;
	}

	public void setChildspec1(Integer childspec1) {
		this.childspec1 = childspec1;
	}

	public Integer getChildspec2() {
		return childspec2;
	}

	public void setChildspec2(Integer childspec2) {
		this.childspec2 = childspec2;
	}

	public Integer getChildspec3() {
		return childspec3;
	}

	public void setChildspec3(Integer childspec3) {
		this.childspec3 = childspec3;
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
		if (!(object instanceof Mapping)) {
			return false;
		}
		ADSLIPTVMapping other = (ADSLIPTVMapping) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "moose.dao.Mapping[id=" + id + "]";
	}

}
