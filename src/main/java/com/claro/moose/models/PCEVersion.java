package com.claro.moose.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PCEVERSION", schema = "MOOSE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PCEVersion {

    @Id
    @Column(name="ID")
    private Long id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PCEVersion other = (PCEVersion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}