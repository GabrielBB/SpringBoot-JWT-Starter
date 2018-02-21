package com.claro.moose.models;

import java.io.Serializable;

/**
 * @author Stainley Lebron
 * @since on 7/2/15.
 */
public class TipoOrdenRebote implements Serializable {

    private String tipoOrden;
    private Integer total;

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
