package com.claro.moose.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQueries({
	@NamedQuery(name="SOCCMapping.Mapping",query="SELECT o FROM SOCCMapping o WHERE o.component = :oferta order by o.mappingVersion desc"),
	@NamedQuery(name="SOCCMapping.all",query="SELECT o FROM SOCCMapping o")
})
@NamedNativeQuery(name="SOCCMapping.getAllMaxVersion", query="Select * from soccmapping s where s.component_id in (select distinct c.component_id from soccmapping c) and s.mappingversion = (select max(y.mappingversion) from soccmapping y where y.component_id = s.component_id)", resultSetMapping="SOCCMappingsSet")
@SqlResultSetMapping(name="SOCCMappingsSet", entities={
@EntityResult(entityClass= com.claro.moose.models.SOCCMapping.class, fields = {
@FieldResult(name="id", column="id"),
@FieldResult(name="component", column="component_id"),
@FieldResult(name="pceVersion", column="pceVersion_id"),
@FieldResult(name="mappingVersion", column="mappingVersion")
})})
public class SOCCMapping implements Serializable{

 /**
  * 
  */
 private static final long serialVersionUID = -5104106433333808037L;

 @Id
 @Basic(optional = false)
 @GeneratedValue(strategy = GenerationType.TABLE, generator="SOCCMapping") 
 @TableGenerator(name="SOCCMapping", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		 initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="SOCCMapping")  
 private Long id;
 
 @OneToOne
 private Component component;
 

 @OneToMany(mappedBy = "soccMapping", cascade = CascadeType.ALL)
 @Fetch(FetchMode.SUBSELECT)
 private List<SOCCMappingDetail> detail;
 
 @OneToOne
 private PCEVersion pceVersion;

 private Long mappingVersion;

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
  if (!(object instanceof SOCCMapping)) {
   return false;
  }
  SOCCMapping other = (SOCCMapping) object;
  if ((this.id == null && other.id != null)
    || (this.id != null && !this.id.equals(other.id))) {
   return false;
  }
  return true;
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

 public List<SOCCMappingDetail> getDetail() {
  return detail;
 }

 public void setDetail(List<SOCCMappingDetail> detail) {
  this.detail = detail;
 }

 public void setSOCCMapping(SOCCMapping soccMapping) {
  setSOCCMapping( soccMapping. component, soccMapping. detail,
    soccMapping.pceVersion, soccMapping.mappingVersion);
 }

 public void setSOCCMapping(Component component, List<SOCCMappingDetail> detail,
   PCEVersion pceVersion, Long mappingVersion) {  
  this.component = component;
  this.detail = detail;
  this.pceVersion = pceVersion;
  this.mappingVersion = mappingVersion;
  this.id = null;
 }
 
 
}