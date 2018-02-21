package com.claro.moose.pce.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Stainley Lebron
 * @since on 7/3/15.
 */
public class FechaReboteM6 implements Serializable{

    private Date fecha;
    private Integer total;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
