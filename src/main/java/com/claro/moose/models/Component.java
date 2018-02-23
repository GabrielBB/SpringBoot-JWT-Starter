/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.moose.models;

import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COMPONENT", schema = "MOOSE")
@Data
@NoArgsConstructor
public class Component {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Component")
    @TableGenerator(name = "Component", table = "HIBERNATE_SEQUENCES", allocationSize = 1,
    initialValue = 0, pkColumnName = "SEQUENCE_NAME", valueColumnName = "SEQUENCE_NEXT_HI_VALUE", pkColumnValue = "COMPONENT")

    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "catalogid", unique = true)
    private Long catalogId;

    @Basic(optional = false)
    @Column(name = "compcode")
    private String compCode;

    @Column(name = "name")
    private String name;

    @Column(name = "servicetype")
    private String serviceType;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
    private Set<AttributeDomain> listAttrsDomains;

    @ManyToOne
    @JoinColumn(name = "pceversion_id")
    private PCEVersion pceVersion;

    @Column(name = "itemtype")
    private String itemType;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
}
