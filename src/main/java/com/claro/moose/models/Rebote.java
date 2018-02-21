package com.claro.moose.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created Stainley Lebron
 * @since on 7/2/15.
 */
public class Rebote implements Serializable{

    private Long oaid;
    private Date creationDate;
    private String productType;
    private String offerName;
    private String tipoRebote;
    private String error;
    private String orderActionType;
    private Date fecha;
    private Long order;
    private Date oaCompletionDate;
    private String mensaje;
    private String CustomerSegment;

    public Long getOaid() {
        return oaid;
    }

    public void setOaid(Long oaid) {
        this.oaid = oaid;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getTipoRebote() {
        return tipoRebote;
    }

    public void setTipoRebote(String tipoRebote) {
        this.tipoRebote = tipoRebote;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOrderActionType() {
        return orderActionType;
    }

    public void setOrderActionType(String orderActionType) {
        this.orderActionType = orderActionType;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Date getOaCompletionDate() {
        return oaCompletionDate;
    }

    public void setOaCompletionDate(Date oaCompletionDate) {
        this.oaCompletionDate = oaCompletionDate;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the CustomerSegment
     */
    public String getCustomerSegment() {
        return CustomerSegment;
    }

    /**
     * @param CustomerSegment the CustomerSegment to set
     */
    public void setCustomerSegment(String CustomerSegment) {
        this.CustomerSegment = CustomerSegment;
    }
}
