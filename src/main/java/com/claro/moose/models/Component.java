/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.moose.pce.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Nestor Alduey
 */
@Entity
@Table(name = "COMPONENT")
@NamedQueries({
    @NamedQuery(name = "Component.findAll", query = "SELECT c FROM Component c WHERE c.itemType = :itemType"),
    @NamedQuery(name = "Component.findByCatalogid", query = "SELECT c FROM Component c WHERE c.catalogId = :catalogId"),
    @NamedQuery(name = "Component.findByCompCode", query = "SELECT c FROM Component c WHERE c.compCode = :compCode"),
    @NamedQuery(name = "Component.findByName", query = "SELECT c FROM Component c WHERE c.name = :name"),
    @NamedQuery(name = "Component.findUnmapped", query = "SELECT o FROM Component o WHERE o NOT IN (SELECT m From Mapping m)"),
    @NamedQuery(name = "Component.findMapped", query = "SELECT o FROM Component o WHERE o IN (SELECT m From Mapping m)"),
    @NamedQuery(name = "Component.findComponent", query = "SELECT o FROM Component o WHERE o.catalogId =:catalogId AND o.pceVersion = :pceVersion"),
    @NamedQuery(name = "Component.findCID", query = "SELECT o FROM Component o WHERE o.catalogId =:catalogId")
})
@NamedNativeQuery(name = "Component.getChilds", query = "SELECT c.id, c.catalogId, c.compCode, c.name, c.serviceType, c.pceVersion_id, c.itemType FROM Component c, Component_Component cc WHERE  c.id = cc.component_id and cc.parentcomponent_id = :parentComponent", resultSetMapping = "ChildsMapping")
@SqlResultSetMapping(name = "ChildsMapping", entities = {
    @EntityResult(entityClass = com.claro.moose.pce.domain.Component.class, fields = {
        @FieldResult(name = "id", column = "id"),
        @FieldResult(name = "catalogId", column = "catalogId"),
        @FieldResult(name = "compCode", column = "compCode"),
        @FieldResult(name = "name", column = "name"),
        @FieldResult(name = "serviceType", column = "serviceType"),
        @FieldResult(name = "pceVersion", column = "pceVersion_id"),
        @FieldResult(name = "itemType", column = "itemType")
    })})
public class Component implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3806499788627635094L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Component")
    @TableGenerator(name = "Component", table = "HIBERNATE_SEQUENCES", allocationSize = 1,
    initialValue = 0, pkColumnName = "SEQUENCE_NAME", valueColumnName = "SEQUENCE_NEXT_HI_VALUE", pkColumnValue = "COMPONENT")
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "catalogId", unique = true)
    private Long catalogId;
    @Basic(optional = false)
    @Column(name = "compCode")
    private String compCode;
    @Column(name = "name")
    private String name;
    @Column(name = "serviceType")
    private String serviceType;
    @ManyToMany
    @JoinColumn(nullable = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Component> parentComponent;
    @Transient
    private List<Component> childComponents;
    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AttributeDomain> listAttrsDomains;
    @OneToOne
    private PCEVersion pceVersion;
    private String itemType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<Component> getChildComponents() {
        return childComponents;
    }

    public void setChildComponents(List<Component> comp) {
        this.childComponents = comp;
    }

    public Component() {
    }

    public Component(Long catalogid) {
        this.catalogId = catalogid;
    }

    public Component(Component comp) {
        this(comp.id, comp.catalogId, comp.compCode, comp.name,
                comp.serviceType, comp.parentComponent,
                comp.childComponents, comp.getListAttrsDomains(),
                comp.pceVersion);
    }

    public Component(Long id, Long catalogId, String compCode, String name,
            String serviceType, List<Component> parentComponent,
            List<Component> childComponents, Set<AttributeDomain> listAttrsDomains,
            PCEVersion pceVersion) {
        super();
        this.id = id;
        this.catalogId = catalogId;
        this.compCode = compCode;
        this.name = name;
        this.serviceType = serviceType;
        this.parentComponent = parentComponent;
        this.childComponents = childComponents;
        this.setListAttrsDomains(listAttrsDomains);
        this.pceVersion = pceVersion;
    }

    public Component(Long catalogid, String compCode) {
        this.catalogId = catalogid;
        this.compCode = compCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PCEVersion getPceVersion() {
        return pceVersion;
    }

    public void setPceVersion(PCEVersion pceVersion) {
        this.pceVersion = pceVersion;
    }

    public Long getCatalogid() {
        return catalogId;
    }

    public void setCatalogid(Long catalogid) {
        this.catalogId = catalogid;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServicetype() {
        return serviceType;
    }

    public void setServicetype(String servicetype) {
        this.serviceType = servicetype;
    }

    public List<Component> getParentcomponent() {
        return parentComponent;
    }

    public void setParentcomponent(List<Component> parentComponent) {
        this.parentComponent = parentComponent;
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
        if (!(object instanceof Component)) {
            return false;
        }
        Component other = (Component) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id
                .equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setListAttrsDomains(Set<AttributeDomain> listAttrsDomains) {
        this.listAttrsDomains = listAttrsDomains;
    }

    public Set<AttributeDomain> getListAttrsDomains() {
        if (listAttrsDomains == null) listAttrsDomains = new HashSet<AttributeDomain>();
        return listAttrsDomains;
    }
}
