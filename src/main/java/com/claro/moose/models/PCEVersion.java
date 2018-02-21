package com.claro.moose.pce.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PCEVERSION")
@NamedQueries({
    @NamedQuery(name = "PCEVersion.lastVersion", query = "SELECT max(c.id) FROM PCEVersion c"),
    @NamedQuery(name = "PCEVersion.getVersion", query = "Select o From PCEVersion o Where o.id = :pceVersion"),
    @NamedQuery(name = "PCEVersion.getVersions", query = "SELECT o FROM PCEVersion o")
})
public class PCEVersion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3626452621580143890L;
    @Id
    @Column(name="ID")
    private Long id;

    public PCEVersion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PCEVersion other = (PCEVersion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }



    public PCEVersion(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}