package com.claro.moose.pce.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String mensajeError;
    @NotNull
    @Column(name = "TIPO_ERROR")
    private String tipoerror;

    @Transient
    private int total;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getTipoError() {
        return tipoerror;
    }

    public void setTipoError(String tipoError) {
        this.tipoerror = tipoError;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
