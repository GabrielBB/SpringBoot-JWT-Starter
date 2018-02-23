/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.claro.moose.models;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ATTRIBUTE")
@NamedNativeQueries({
		@NamedNativeQuery(name = "Component.getAttrs", query = "select a.id, a.domain_id, a.name, a.pceversion_id, a.propertyid, a.propertyname from attribute a, COMPONENT_ATTRIBUTE c, component co where a.id =  c.LISTATTRS_ID and c.component_id = co.id and co.catalogid = :catalogid", resultSetMapping = "ChildsAttrs"),
		@NamedNativeQuery(name = "Attr.getDom", query = "SELECT e.id, e.value, e.decodedvalue, e.domain_id from  entry e where e.domain_id = :domain", resultSetMapping = "OldEnt"),
		@NamedNativeQuery(name = "Attr.getOldEnt", query = "SELECT e.id, e.value, e.decodedvalue, e.domain_id from attribute_entry c, entry e where c.oldentries_id = e.id and c.attribute_id = :attri", resultSetMapping = "OldEnt"),
		@NamedNativeQuery(name = "Component.getAttrsDomain", query = "select a.id, a.attribute_id, a.component_id, a.domain_id from ATTRIBUTEDOMAIN a WHERE  A.COMPONENT_ID = :componentid", resultSetMapping = "ChildsAttrsDom") })
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "OldEnt", entities = { @EntityResult(entityClass = com.claro.moose.models.Entry.class, fields = {
				@FieldResult(name = "id", column = "id"),
				@FieldResult(name = "value", column = "value"),
				@FieldResult(name = "decodedvalue", column = "decodedvalue"),
				@FieldResult(name = "models", column = "domain_id") }) }),
		@SqlResultSetMapping(name = "ChildsAttrsDom", entities = { @EntityResult(entityClass = com.claro.moose.models.AttributeDomain.class, fields = {
				@FieldResult(name = "id", column = "id"),
				@FieldResult(name = "attribute", column = "attribute_id"),
				@FieldResult(name = "component", column = "component_id"),
				@FieldResult(name = "models", column = "domain_id") }) }),
		@SqlResultSetMapping(name = "ChildsAttrs", entities = { @EntityResult(entityClass = com.claro.moose.models.Attribute.class, fields = {
				@FieldResult(name = "id", column = "id"),
				@FieldResult(name = "propertyid", column = "propertyid"),
				@FieldResult(name = "name", column = "name"),
				@FieldResult(name = "propertyname", column = "propertyname"),
				@FieldResult(name = "pceVersion", column = "pceversion_id"),
				@FieldResult(name = "models", column = "domain_id") }) }) })
public class Attribute {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Attribute")
	@TableGenerator(name = "Attribute", table = "HIBERNATE_SEQUENCES", allocationSize = 1, initialValue = 0, pkColumnName = "SEQUENCE_NAME", valueColumnName = "SEQUENCE_NEXT_HI_VALUE", pkColumnValue = "ATTRIBUTE")
	@Column(name = "id")
	private Long id;

	@Column(name = "propertyid")
	private Integer propertyid;

	@Column(name = "name")
	private String name;

	@Column(name = "propertyname")
	private String propertyname;
	
	@OneToOne
	@JoinColumn(name = "pceversion_id")
	private PCEVersion pceVersion;

	@OneToOne
	@JoinColumn(name = "domain_id")
	private Domain domain;

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
