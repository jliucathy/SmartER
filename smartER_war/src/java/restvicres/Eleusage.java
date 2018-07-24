/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restvicres;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ljx
 */
@Entity
@Table(name = "ELEUSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eleusage.findAll", query = "SELECT e FROM Eleusage e")
    , @NamedQuery(name = "Eleusage.findByUsageid", query = "SELECT e FROM Eleusage e WHERE e.usageid = :usageid")
    , @NamedQuery(name = "Eleusage.findByDate", query = "SELECT e FROM Eleusage e WHERE e.date = :date")
    , @NamedQuery(name = "Eleusage.findByTime", query = "SELECT e FROM Eleusage e WHERE e.time = :time")
    , @NamedQuery(name = "Eleusage.findByFridge", query = "SELECT e FROM Eleusage e WHERE e.fridge = :fridge")
    , @NamedQuery(name = "Eleusage.findByAircon", query = "SELECT e FROM Eleusage e WHERE e.aircon = :aircon")
    , @NamedQuery(name = "Eleusage.findByWashingmachine", query = "SELECT e FROM Eleusage e WHERE e.washingmachine = :washingmachine")
    , @NamedQuery(name = "Eleusage.findByTemperature", query = "SELECT e FROM Eleusage e WHERE e.temperature = :temperature")
    , @NamedQuery(name = "Eleusage.findByEmailANDTime", query = "SELECT e FROM Eleusage e WHERE UPPER(e.resid.email) = UPPER(:email) AND e.time=:time")})
public class Eleusage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USAGEID")
    private String usageid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    private int time;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FRIDGE")
    private BigDecimal fridge;
    @Column(name = "AIRCON")
    private BigDecimal aircon;
    @Column(name = "WASHINGMACHINE")
    private BigDecimal washingmachine;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURE")
    private BigDecimal temperature;
    @JoinColumn(name = "RESID", referencedColumnName = "RESID")
    @ManyToOne
    private Resident resid;

    public Eleusage() {
    }

    public Eleusage(String usageid) {
        this.usageid = usageid;
    }

    public Eleusage(String usageid, Date date, int time, BigDecimal temperature) {
        this.usageid = usageid;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
    }

    public String getUsageid() {
        return usageid;
    }

    public void setUsageid(String usageid) {
        this.usageid = usageid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public BigDecimal getFridge() {
        return fridge;
    }

    public void setFridge(BigDecimal fridge) {
        this.fridge = fridge;
    }

    public BigDecimal getAircon() {
        return aircon;
    }

    public void setAircon(BigDecimal aircon) {
        this.aircon = aircon;
    }

    public BigDecimal getWashingmachine() {
        return washingmachine;
    }

    public void setWashingmachine(BigDecimal washingmachine) {
        this.washingmachine = washingmachine;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usageid != null ? usageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eleusage)) {
            return false;
        }
        Eleusage other = (Eleusage) object;
        if ((this.usageid == null && other.usageid != null) || (this.usageid != null && !this.usageid.equals(other.usageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restvicres.Eleusage[ usageid=" + usageid + " ]";
    }
    
}
