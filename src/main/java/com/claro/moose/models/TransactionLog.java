package com.claro.moose.models;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author Edgar Garcia
 */
@Entity
public class TransactionLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="TransactionLog")
	@TableGenerator(name="TransactionLog", table="HIBERNATE_SEQUENCES", allocationSize=1, 
		 initialValue=0, pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_NEXT_HI_VALUE", pkColumnValue="TransactionLog")    
    private Long id;
    private String usuario;
    private String ip;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTime;
    private String codTrans;
    private String descripcion;

    public String getCodTrans() {
        return codTrans;
    }

    public void setCodTrans(String codTrans) {
        this.codTrans = codTrans;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof TransactionLog)) {
            return false;
        }
        TransactionLog other = (TransactionLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "claro.moose.log.TransactionLog[id=" + id + "]";
    }

}
