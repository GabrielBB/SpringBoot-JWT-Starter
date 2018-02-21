/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.moose.pce.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author Nestor Alduey
 */
@Entity
@Table(name = "MAPPING")
@NamedQueries({
    @NamedQuery(name = "Mapping.getMapping", query = "Select o from Mapping o where o.componentPath = :componentPath"),
    @NamedQuery(name = "Mapping.getComponent", query = "Select o from Mapping o where o.component = :component and o.componentPath = :componentPath order by o.mappingVersion desc"),
    @NamedQuery(name = "Mapping.MappingSequence", query = "Select count(o) from Mapping o where o.component = :component"),
    @NamedQuery(name = "Mapping.MappingSequencePath", query = "Select count(o) from Mapping o where o.componentPath = :componentpath"),
    @NamedQuery(name = "Mapping.MappingSequencePathNull", query = "Select count(o) from Mapping o where o.componentPath is null and o.component = :component"),
    @NamedQuery(name = "Mapping.getAll", query = "Select o from Mapping o"),
    @NamedQuery(name = "Mapping.getComponentPathNull", query = "Select o from Mapping o where o.component = :component and o.componentPath is null order by o.mappingVersion desc"),
    @NamedQuery(name = "Mapping.getMappingByComponent", query = "Select o from Mapping o where o.component = :component")
})
@NamedNativeQuery(name = "Mapping.getAllMaxVersion", query = "select * from mapping m where m.componentpath in (select distinct c.componentpath from mapping c where c.componentpath is not null)"
        + "  and m.mappingversion = (select max(q.mappingversion) from mapping q where q.componentpath = m.componentpath) "
        + " UNION ALL select * from mapping m where m.component_id in (select distinct c.component_id from mapping c where c.componentpath is null)"
        + "  and m.mappingversion = (select max(q.mappingversion) from mapping q where q.component_id = m.component_id)", resultSetMapping = "MappingsSet")
@SqlResultSetMapping(name = "MappingsSet", entities = {
    @EntityResult(entityClass = com.claro.moose.pce.domain.Mapping.class, fields = {
        @FieldResult(name = "id", column = "id"),
        @FieldResult(name = "component", column = "component_id"),
        @FieldResult(name = "gsec", column = "gsec_id"),
        @FieldResult(name = "parentspec", column = "parentspec"),
        @FieldResult(name = "childspec1", column = "childspec1"),
        @FieldResult(name = "childspec2", column = "childspec2"),
        @FieldResult(name = "childspec3", column = "childspec3"),
        @FieldResult(name = "componentPath", column = "componentPath"),
        @FieldResult(name = "isCombined", column = "isCombined"),
        @FieldResult(name = "customerType", column = "customerType_id"),
        @FieldResult(name = "ism6plan", column = "ism6plan"),
        @FieldResult(name = "excludeOnAction", column = "excludeOnAction"),
        @FieldResult(name = "pceVersion", column = "pceVersion_id"),
        @FieldResult(name = "mappingVersion", column = "mappingVersion"),
        @FieldResult(name = "planID", column = "planid"),
        @FieldResult(name = "resourceID", column = "resourceid"),
        @FieldResult(name = "expansionSaydot", column = "expansionsaydot"),
        @FieldResult(name = "isAuthorized", column = "ISAUTHORIZED")
    })})
public class Mapping implements Serializable {

    private static final long serialVersionUID = 274155726721814921L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Mapping")
    @TableGenerator(name = "Mapping", table = "HIBERNATE_SEQUENCES", allocationSize = 1,
            initialValue = 0, pkColumnName = "SEQUENCE_NAME", valueColumnName = "SEQUENCE_NEXT_HI_VALUE", pkColumnValue = "MAPPING")
    private Long id;
    @OneToOne
    private Component component;
    @OneToOne
    private GSEC gsec;
    private Integer parentspec;
    private Integer childspec1;
    private Integer childspec2;
    private Integer childspec3;
    @Basic(optional = true)
    private String componentPath;
    @Basic(optional = true)
    private boolean isCombined;
    @Basic(optional = true)
    private String planID;
    @Basic(optional = true)
    private String resourceID;

    @Basic(optional = true)
    private String expansionSaydot;

    //ISAUTHORIZED
    @Basic(optional = true)
    private boolean isAuthorized;

    @ManyToMany
    private List<TargetSystem> targetsystem;
    @OneToOne
    private CustomerType customerType;
    private boolean ism6plan;
    private String excludeOnAction;
    @OneToMany(mappedBy = "mapping", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MappingAttributes> attributes;
    @OneToOne
    private PCEVersion pceVersion;
    private Long mappingVersion;

    public boolean isCombined() {
        return isCombined;
    }

    public void setCombined(boolean isCombined) {
        this.isCombined = isCombined;
    }

    public String getComponentPath() {
        return componentPath;
    }

    public void setComponentPath(String componentPath) {
        this.componentPath = componentPath;
    }

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

    public List<MappingAttributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MappingAttributes> attributes) {
        this.attributes = attributes;
        //System.out.println("Size of Attributes List: " + attributes.size());
    }

    public Mapping() {
    }

    public void setMapping(Mapping mapping) {
        this.attributes = mapping.getAttributes();
        this.component = mapping.getComponent();
        this.targetsystem = mapping.getTargetsystem();
        //this.id = mapping.getId();
        this.gsec = mapping.getGsec();
        this.parentspec = mapping.getParentspec();
        this.childspec1 = mapping.getChildspec1();
        this.childspec2 = mapping.getChildspec2();
        this.childspec3 = mapping.getChildspec3();
        this.customerType = mapping.getCustomertype();
        this.ism6plan = mapping.getIsm6plan();
        this.excludeOnAction = mapping.getExcludeonaction();
        this.pceVersion = mapping.getPceVersion();
        this.id = null;
        this.componentPath = mapping.getComponentPath();
        this.mappingVersion = mapping.getMappingVersion();
        this.isCombined = mapping.isCombined;
        this.planID = mapping.getPlanID();
        this.resourceID = mapping.getResourceID();
        this.expansionSaydot = mapping.getExpansionSaydot();
        this.isAuthorized = mapping.isIsAuthorized();
    }

    public Mapping(Long id) {
        this.id = id;
    }

    public Mapping(Long id, Component component, List<TargetSystem> targetsystem) {
        this.id = id;
        this.component = component;
        this.targetsystem = targetsystem;
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

    public GSEC getGsec() {
        return gsec;
    }

    public void setGsec(GSEC gsec) {
        this.gsec = gsec;
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

    public List<TargetSystem> getTargetsystem() {
//        return targetsystem == null ? new ArrayList<TargetSystem>() : targetsystem;
        return targetsystem;
    }

    public void setTargetsystem(List<TargetSystem> targetsystem) {
        this.targetsystem = targetsystem;
    }

    public CustomerType getCustomertype() {
        return customerType;
    }

    public void setCustomertype(CustomerType customertype) {
        this.customerType = customertype;
    }

    public boolean getIsm6plan() {
        return ism6plan;
    }

    public void setIsm6plan(boolean ism6plan) {
        this.ism6plan = ism6plan;
    }

    public String getExcludeonaction() {
        return excludeOnAction;
    }

    public void setExcludeonaction(String excludeOnAction) {
        this.excludeOnAction = excludeOnAction;
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
        Mapping other = (Mapping) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return componentPath == null ? "" : componentPath;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return the planID
     */
    public String getPlanID() {
        return planID;
    }

    /**
     * @param planID the planID to set
     */
    public void setPlanID(String planID) {
        this.planID = planID;
    }

    /**
     * @return the resourceID
     */
    public String getResourceID() {
        return resourceID;
    }

    /**
     * @param resourceID the resourceID to set
     */
    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    /**
     * @return the expansionSaydot
     */
    public String getExpansionSaydot() {
        return expansionSaydot;
    }

    /**
     * @param expansionSaydot the expansionSaydot to set
     */
    public void setExpansionSaydot(String expansionSaydot) {
        this.expansionSaydot = expansionSaydot;
    }

    /**
     * @return the isAuthorized
     */
    public boolean isIsAuthorized() {
        return isAuthorized;
    }

    /**
     * @param isAuthorized the isAuthorized to set
     */
    public void setIsAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    /**
     * @return the isParallelsPlan
     */
}
