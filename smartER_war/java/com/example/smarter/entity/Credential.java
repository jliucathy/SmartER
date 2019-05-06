package com.example.smarter.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.smarter.util.DateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;
//import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "credential")
//@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Credential.findAll", query = "SELECT c FROM Credential c")
        , @NamedQuery(name = "Credential.findByUsername", query = "SELECT c FROM Credential c WHERE c.username = :username")
        , @NamedQuery(name = "Credential.findByPassword", query = "SELECT c FROM Credential c WHERE c.password = :password")
        , @NamedQuery(name = "Credential.findByRegistrationdate", query = "SELECT c FROM Credential c WHERE c.registrationdate = :registrationdate")})

public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private Resident resid;
    private String password;
    private Date registrationdate;

    public Credential() {
    }

    public Credential(String username) {
        this.username = username;
    }

    public Credential(String username, String password, Date registrationdate) {
        this.username = username;
        this.password = password;
        this.registrationdate = registrationdate;
    }

    @Id
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "USERNAME")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToOne
    @JoinColumn(name = "RESID", referencedColumnName = "RESID")
    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic(optional = false)
    @NotNull
    @Column(name = "REGISTRATIONDATE")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="UTC")
    public Date getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return resid == that.resid &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(registrationdate, that.registrationdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, resid, password, registrationdate);
    }
    
    @Override
    public String toString() {
        return "Credential {username=" + username + ", password=" + password + 
        		", registrationdate=" + DateConverter.convertDate(registrationdate) + ", resid=" +resid +
        		 "}";
    }
}

