/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.moose.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@NoArgsConstructor
@Getter
@Setter
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
            name.append("-" + value.getDecodedValue());
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


}
