/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.moose.pce.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author Nestor Alduey
 */
@Entity
@Table(name = "MAPPING_ATTRIBUTES")
public class MappingAttributes implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3847911976894538808L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MappingAttributes")
    @TableGenerator(name = "MappingAttributes", table = "HIBERNATE_SEQUENCES", allocationSize = 1,
            initialValue = 0, pkColumnName = "SEQUENCE_NAME", valueColumnName = "SEQUENCE_NEXT_HI_VALUE", pkColumnValue = "MAPPING_ATTRIBUTES")
    private Long id;
    @OneToOne(optional = true)
    private Attribute attribute;
    @OneToOne(optional = true)
    private Entry value;
    @Column(name = "ISDYNAMIC", nullable = false)
    private boolean isDynamic;
    @Column(name = "LABEL", nullable = true)
    private String label;
    @OneToOne(optional = true)
    private GSEC gsec;
    private String excludeOnAction;
    private boolean isM6Plan;
    private Integer parentspec;
    private Integer childspec1;
    private Integer childspec2;
    private Integer childspec3;
    @OneToOne
    private CustomerType custType;
    @Column(name = "sequence")
    private int sequence;
    @ManyToOne
    private Mapping mapping;
    @Column(name = "isDefault", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "isQuantity", nullable = false)
    private Boolean isQuantity = false;

    @Basic(optional = true)
    private String planID;
    @Basic(optional = true)
    private String resourceID;

    public boolean isM6Plan() {
        return isM6Plan;
    }

    public void setM6Plan(boolean isM6Plan) {
        this.isM6Plan = isM6Plan;
    }

    public String getExcludeonaction() {
        return excludeOnAction;
    }

    public void setExcludeonaction(String excludeOnAction) {
        this.excludeOnAction = excludeOnAction;
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

    public CustomerType getCustomerType() {
        return custType;
    }

    public void setCustomerType(CustomerType custType) {
        this.custType = custType;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

    public MappingAttributes() {
    }

    public MappingAttributes(String label, boolean isDynamic) {
        this.label = label;
        this.isDynamic = isDynamic;
    }

    public MappingAttributes(long id) {
        this.id = id;

    }

    public MappingAttributes(Mapping mappping, int sequence) {
        this.mapping = mappping;
        this.sequence = sequence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Entry getValue() {
        return value;
    }

    public void setValue(Entry value) {
        this.value = value;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean isDynamic) {
        this.isDynamic = isDynamic;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MappingAttributes)) {
            return false;
        }
        MappingAttributes other = (MappingAttributes) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id
                .equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder name = new StringBuilder();

        if (attribute != null) {
            name.append(attribute.getName());
        }
        if (value != null) {
            name.append("-" + value.getDecodedvalue());
        }
        if (gsec != null) {
            name.append("-" + gsec.getNombre());
        }
        if (custType != null) {
            name.append("-" + custType.getName());
        }
        if (parentspec != null) {
            name.append("-" + parentspec.toString());
        }
        if (childspec1 != null) {
            name.append("-" + childspec1.toString());
        }
        if (childspec2 != null) {
            name.append("-" + childspec2.toString());
        }
        if (childspec3 != null) {
            name.append("-" + childspec3.toString());
        }
        if (excludeOnAction != null && !excludeOnAction.equals("")) {
            name.append("-" + excludeOnAction);
        }
        if (isM6Plan) {
            name.append("- Es Plan");
        }
        if (isDynamic) {
            name.append("- Dinamico");
        }

        if (planID != null && !"".equals(planID)) {
            name.append("- PlanID = " + planID);
        }

        if (resourceID != null && !"".equals(resourceID)) {
            name.append("- ResourceID = " + resourceID);
        }

        if (isQuantity) {
            name.append("- Requiere Autorizacion");
        }
        return name.toString();
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
     * @return the isAuthorized
     */
    public boolean isIsQuantity() {
        return isQuantity;
    }

    /**
     * @param isQuantity
     */
    public void setIsQuantity(boolean isQuantity) {
        this.isQuantity = isQuantity;
    }

    /**
     * @return the isParallelsPlan
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * @param isParallelsPlan the isParallelsPlan to set
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}
