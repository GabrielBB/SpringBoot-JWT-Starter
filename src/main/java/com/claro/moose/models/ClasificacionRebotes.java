package com.claro.moose.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Stainley Lebron
 * @since on 6/15/15.
 */

@Entity
@Table(name = "CLASIFICACION_REBOTES")
@NamedQuery(name = "clasificacion.all", query = "select c from ClasificacionRebotes c")
public class ClasificacionRebotes implements Serializable {

    @Id
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "MENSAJE_ERROR")
    private String mensajeError;
    @Column(name = "TIPO_ERROR")
    private String tipoerror;
    @Transient
    private int total;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ErrorType)) {
            return false;
        }
        ClasificacionRebotes clasificacionRebote = (ClasificacionRebotes) object;
        if ((this.id == null && clasificacionRebote.id != null) || (this.id != null && !this.id.equals(clasificacionRebote.id))) {
            return false;
        }
        return true;
    }

}
