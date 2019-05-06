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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Comparator;
//import java.text.DecimalFormat;

import com.example.smarter.util.DateConverter;

@Entity
@Table(name = "ELEUSAGE")
@NamedQueries({
        @NamedQuery(name = "Eleusage.findAll", query = "SELECT e FROM Eleusage e")
        , @NamedQuery(name = "Eleusage.findByUsageid", query = "SELECT e FROM Eleusage e WHERE e.usageid = :usageid")
        , @NamedQuery(name = "Eleusage.findByDate", query = "SELECT e FROM Eleusage e WHERE e.date = :date")
        , @NamedQuery(name = "Eleusage.findByTime", query = "SELECT e FROM Eleusage e WHERE e.time = :time")
        , @NamedQuery(name = "Eleusage.findByFridge", query = "SELECT e FROM Eleusage e WHERE e.fridge = :fridge")
        , @NamedQuery(name = "Eleusage.findByAircon", query = "SELECT e FROM Eleusage e WHERE e.aircon = :aircon")
        , @NamedQuery(name = "Eleusage.findByWashingmachine", query = "SELECT e FROM Eleusage e WHERE e.washingmachine = :washingmachine")
        , @NamedQuery(name = "Eleusage.findByTemperature", query = "SELECT e FROM Eleusage e WHERE e.temperature = :temperature")
        , @NamedQuery(name = "Eleusage.findByDateTime", query = "SELECT e FROM Eleusage e WHERE e.date = :date AND e.time = :time")
        , @NamedQuery(name = "Eleusage.findByResidDate", query = "SELECT e FROM Eleusage e WHERE e.resid.resid = :resid AND e.date = :date")
        , @NamedQuery(name = "Eleusage.findByEmailANDTime", query = "SELECT e FROM Eleusage e WHERE UPPER(e.resid.email) = UPPER(:email) AND e.time=:time")
        , @NamedQuery(name = "Eleusage.findByResidDateTime", query = "SELECT e.fridge,e.aircon,e.washingmachine "
        		+ "FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time")
        })

public class Eleusage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String usageid;
    private Resident resid;
    private Date date;
    private int time;
    private BigDecimal temperature;
    private BigDecimal aircon;
    private BigDecimal washingmachine;
    private BigDecimal fridge;

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

    @Id
    @Column(name = "USAGEID")
    @NotNull
    @Size(min = 1, max = 20)
    public String getUsageid() {
        return usageid;
    }

    public void setUsageid(String usageid) {
        this.usageid = usageid;
    }

    @JoinColumn(name = "RESID", referencedColumnName = "RESID")
    @ManyToOne
    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="UTC")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURE")
    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    @Basic
    @Column(name = "AIRCON")
    public BigDecimal getAircon() {
        return aircon;
    }

    public void setAircon(BigDecimal aircon) {
        this.aircon = aircon;
    }

    @Basic
    @Column(name = "WASHINGMACHINE")
    public BigDecimal getWashingmachine() {
        return washingmachine;
    }

    public void setWashingmachine(BigDecimal washingmachine) {
        this.washingmachine = washingmachine;
    }

    @Basic
    @Column(name = "FRIDGE")
    public BigDecimal getFridge() {
        return fridge;
    }

    public void setFridge(BigDecimal fridge) {
        this.fridge = fridge;
    }

    // total usage for a specific hour on the day of the resident
    public double retrieveTotalUsage(){
        double totalTemp = getFridge().doubleValue() + getAircon().doubleValue() + getWashingmachine().doubleValue();
        java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
        String strtemp=df.format(totalTemp);
        double total = Double.parseDouble(strtemp);
        return total;
    }
    
    //Comparator for sorting the list by total usage
    public static Comparator<Eleusage> totalUsageCom = new Comparator<Eleusage>() {
    	public int compare(Eleusage e1, Eleusage e2) {
    		int total1 = (int) e1.retrieveTotalUsage();
    		int total2 = (int) e2.retrieveTotalUsage();
    		return total1-total2;
    	}
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eleusage eleusage = (Eleusage) o;
        return time == eleusage.time &&
                Objects.equals(usageid, eleusage.usageid) &&
                Objects.equals(resid, eleusage.resid) &&
                Objects.equals(date, eleusage.date) &&
                Objects.equals(temperature, eleusage.temperature) &&
                Objects.equals(aircon, eleusage.aircon) &&
                Objects.equals(washingmachine, eleusage.washingmachine) &&
                Objects.equals(fridge, eleusage.fridge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usageid, resid, date, time, temperature, aircon, washingmachine, fridge);
    }
    
    @Override
    public String toString() {
        return "Eleusage {usageid=" + usageid + ", resid=" + String.valueOf(resid.getResid()) + 
        		", date=" + DateConverter.convertDate(date)+ ", temperature=" +String.valueOf(temperature) + ", air conditioner" + String.valueOf(aircon)
        		+", washing machine" + String.valueOf(washingmachine) + ", fridge" + String.valueOf(fridge) +
        		 "}";
    }
}
