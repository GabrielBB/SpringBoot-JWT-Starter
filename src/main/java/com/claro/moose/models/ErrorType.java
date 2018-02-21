/*
 * Copyright 2014 Claro Dominicana.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.claro.moose.pce.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dulcar Vasquez
 */
@Entity
@Table(name = "ERROR_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ErrorType.findAll", query = "SELECT e FROM ErrorType e"),
    @NamedQuery(name = "ErrorType.findById", query = "SELECT e FROM ErrorType e WHERE e.id = :id"),
    @NamedQuery(name = "ErrorType.findByType", query = "SELECT e FROM ErrorType e WHERE e.type = :type"),
    @NamedQuery(name = "ErrorType.findBySendAdmin", query = "SELECT e FROM ErrorType e WHERE e.sendAdmin = :sendAdmin"),
    @NamedQuery(name = "ErrorType.findByEmails", query = "SELECT e FROM ErrorType e WHERE e.emails = :emails")})
public class ErrorType implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ERROR")
    private String error;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USER_MESSAGE")
    private String userMessage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEND_ADMIN")
    private boolean sendAdmin;
    @Size(max = 255)
    @Column(name = "ADMIN_MESSAGE")
    private String adminMessage;
    @Size(max = 255)
    @Column(name = "EMAILS")
    private String emails;

    public ErrorType() {
    }

    public ErrorType(BigDecimal id) {
        this.id = id;
    }

    public ErrorType(BigDecimal id, String error, String type, String userMessage, boolean sendAdmin) {
        this.id = id;
        this.error = error;
        this.type = type;
        this.userMessage = userMessage;
        this.sendAdmin = sendAdmin;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public boolean getSendAdmin() {
        return sendAdmin;
    }

    public void setSendAdmin(boolean sendAdmin) {
        this.sendAdmin = sendAdmin;
    }

    public String getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        this.adminMessage = adminMessage;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
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
        ErrorType other = (ErrorType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.claro.moose.pce.domain.ErrorType[ id=" + id + " ]";
    }
    
}
