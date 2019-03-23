/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restvicres;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ljx
 */
@Entity
@Table(name = "RESIDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resident.findAll", query = "SELECT r FROM Resident r")
    , @NamedQuery(name = "Resident.findByResid", query = "SELECT r FROM Resident r WHERE r.resid = :resid")
    , @NamedQuery(name = "Resident.findByFirstname", query = "SELECT r FROM Resident r WHERE r.firstname = :firstname")
    , @NamedQuery(name = "Resident.findBySurname", query = "SELECT r FROM Resident r WHERE r.surname = :surname")
    , @NamedQuery(name = "Resident.findByDob", query = "SELECT r FROM Resident r WHERE r.dob = :dob")
    , @NamedQuery(name = "Resident.findByAddress", query = "SELECT r FROM Resident r WHERE r.address = :address")
    , @NamedQuery(name = "Resident.findByPostcode", query = "SELECT r FROM Resident r WHERE r.postcode = :postcode")
    , @NamedQuery(name = "Resident.findByEmail", query = "SELECT r FROM Resident r WHERE r.email = :email")
    , @NamedQuery(name = "Resident.findByMobile", query = "SELECT r FROM Resident r WHERE r.mobile = :mobile")
    , @NamedQuery(name = "Resident.findByNoofresidents", query = "SELECT r FROM Resident r WHERE r.noofresidents = :noofresidents")
    , @NamedQuery(name = "Resident.findByEnergyprovider", query = "SELECT r FROM Resident r WHERE r.energyprovider = :energyprovider")})
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESID")
    private Integer resid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 16)
    @Column(name = "POSTCODE")
    private String postcode;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 256)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "MOBILE")
    private String mobile;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOOFRESIDENTS")
    private int noofresidents;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ENERGYPROVIDER")
    private String energyprovider;
    @OneToMany(mappedBy = "resid")
    private Collection<Credential> credentialCollection;
    @OneToMany(mappedBy = "resid")
    private Collection<Eleusage> eleusageCollection;

    public Resident() {
    }

    public Resident(Integer resid) {
        this.resid = resid;
    }

    public Resident(Integer resid, String firstname, String surname, String address, String mobile, int noofresidents, String energyprovider) {
        this.resid = resid;
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.mobile = mobile;
        this.noofresidents = noofresidents;
        this.energyprovider = energyprovider;
    }

    public Integer getResid() {
        return resid;
    }

    public void setResid(Integer resid) {
        this.resid = resid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getNoofresidents() {
        return noofresidents;
    }

    public void setNoofresidents(int noofresidents) {
        this.noofresidents = noofresidents;
    }

    public String getEnergyprovider() {
        return energyprovider;
    }

    public void setEnergyprovider(String energyprovider) {
        this.energyprovider = energyprovider;
    }

    @XmlTransient
    public Collection<Credential> getCredentialCollection() {
        return credentialCollection;
    }

    public void setCredentialCollection(Collection<Credential> credentialCollection) {
        this.credentialCollection = credentialCollection;
    }

    @XmlTransient
    public Collection<Eleusage> getEleusageCollection() {
        return eleusageCollection;
    }

    public void setEleusageCollection(Collection<Eleusage> eleusageCollection) {
        this.eleusageCollection = eleusageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resid != null ? resid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resident)) {
            return false;
        }
        Resident other = (Resident) object;
        if ((this.resid == null && other.resid != null) || (this.resid != null && !this.resid.equals(other.resid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restvicres.Resident[ resid=" + resid + " ]";
    }
    
}
