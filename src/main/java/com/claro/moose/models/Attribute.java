/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.pce.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 
 * @author Nestor Alduey
 */
@Entity
@Table(name = "ATTRIBUTE")
@NamedQuery(name = "getAttribute", query = "select a from Attribute a where a.propertyid = :pid")
@NamedNativeQueries({
		@NamedNativeQuery(name = "Component.getAttrs", query = "select a.id, a.domain_id, a.name, a.pceversion_id, a.propertyid, a.propertyname from attribute a, COMPONENT_ATTRIBUTE c, component co where a.id =  c.LISTATTRS_ID and c.component_id = co.id and co.catalogid = :catalogid", resultSetMapping = "ChildsAttrs"),
		@NamedNativeQuery(name = "Attr.getDom", query = "SELECT e.id, e.value, e.decodedvalue, e.domain_id from  entry e where e.domain_id = :domain", resultSetMapping = "OldEnt"),
		@NamedNativeQuery(name = "Attr.getOldEnt", query = "SELECT e.id, e.value, e.decodedvalue, e.domain_id from attribute_entry c, entry e where c.oldentries_id = e.id and c.attribute_id = :attri", resultSetMapping = "OldEnt"),
		@NamedNativeQuery(name = "Component.getAttrsDomain", query = "select a.id, a.attribute_id, a.component_id, a.domain_id from ATTRIBUTEDOMAIN a WHERE  A.COMPONENT_ID = :componentid", resultSetMapping = "ChildsAttrsDom") })
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "OldEnt", entities = { @EntityResult(entityClass = com.claro.moose.pce.domain.Entry.class, fields = {
				@FieldResult(name = "id", column = "id"),
				@FieldResult(name = "value", column = "value"),
				@FieldResult(name = "decodedvalue", column = "decodedvalue"),
				@FieldResult(name = "domain", column = "domain_id") }) }),
		@SqlResultSetMapping(name = "ChildsAttrsDom", entities = { @EntityResult(entityClass = com.claro.moose.pce.domain.AttributeDomain.class, fields = {
				@FieldResult(name = "id", column = "id"),
				@FieldResult(name = "attribute", column = "attribute_id"),
				@FieldResult(name = "component", column = "component_id"),
				@FieldResult(name = "domain", column = "domain_id") }) }),
		@SqlResultSetMapping(name = "ChildsAttrs", entities = { @EntityResult(entityClass = com.claro.moose.pce.domain.Attribute.class, fields = {
				@FieldResult(name = "id", column = "id"),
				@FieldResult(name = "propertyid", column = "propertyid"),
				@FieldResult(name = "name", column = "name"),
				@FieldResult(name = "propertyname", column = "propertyname"),
				@FieldResult(name = "pceVersion", column = "pceversion_id"),
				@FieldResult(name = "domain", column = "domain_id") }) }) })
public class Attribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6265958833184287134L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Attribute")
	@TableGenerator(name = "Attribute", table = "HIBERNATE_SEQUENCES", allocationSize = 1, initialValue = 0, pkColumnName = "SEQUENCE_NAME", valueColumnName = "SEQUENCE_NEXT_HI_VALUE", pkColumnValue = "ATTRIBUTE")
	@Column(name = "id")
	private Long id;

	private Integer propertyid;

	private String name;

	private String propertyname;
	@OneToOne
	private PCEVersion pceVersion;

	@OneToOne
	private Domain domain;

	@ManyToMany(cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(nullable = true)
	private List<Entry> oldEntries;

	public List<Entry> getOldEntry() {
		return oldEntries;
	}

	public void setOldEntry(List<Entry> oldEntries) {
		this.oldEntries = oldEntries;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public List<Entry> getAllEntries() {
		List<Entry> entries = getDomain().getEntry();
		for (Entry old : oldEntries) {
			entries.add(old);
		}
		return entries;
	}

	public PCEVersion getPceVersion() {
		return pceVersion;
	}

	public void setPceVersion(PCEVersion pceVersion) {
		this.pceVersion = pceVersion;
	}

	public Attribute() {

	}

	public Attribute(Integer propertyid) {
		this.propertyid = propertyid;
	}

	public Attribute(Integer propertyid, String name) {
		this.propertyid = propertyid;
		this.name = name;
	}

	public Integer getPropertyid() {
		return propertyid;
	}

	public void setPropertyid(Integer propertyid) {
		this.propertyid = propertyid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPropertyname() {
		return propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != 0 ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Attribute)) {
			return false;
		}
		Attribute other = (Attribute) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
