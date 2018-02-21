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
package com.claro.moose.models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "ERROR")
    private String error;
    @Basic(optional = false)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @Column(name = "USER_MESSAGE")
    private String userMessage;
    @Basic(optional = false)
    @Column(name = "SEND_ADMIN")
    private boolean sendAdmin;
    @Column(name = "ADMIN_MESSAGE")
    private String adminMessage;
    @Column(name = "EMAILS")
    private String emails;

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
        return "com.claro.moose.models.ErrorType[ id=" + id + " ]";
    }
    
}
