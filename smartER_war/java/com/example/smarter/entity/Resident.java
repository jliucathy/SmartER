package com.example.smarter.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import java.util.Date;
import java.util.Objects;
import java.util.Collection;

//import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.example.smarter.util.DateConverter;

@NamedQueries({
        @NamedQuery(name = "Resident.findByFirstname", query = "SELECT r FROM Resident r WHERE r.firstname = :firstname")
        , @NamedQuery(name = "Resident.findBySurname", query = "SELECT r FROM Resident r WHERE r.surname = :surname")
        , @NamedQuery(name = "Resident.findByDob", query = "SELECT r FROM Resident r WHERE r.dob = :dob")
        , @NamedQuery(name = "Resident.findByAddress", query = "SELECT r FROM Resident r WHERE r.address = :address")
        , @NamedQuery(name = "Resident.findByPostcode", query = "SELECT r FROM Resident r WHERE r.postcode = :postcode")
        , @NamedQuery(name = "Resident.findByEmail", query = "SELECT r FROM Resident r WHERE r.email = :email")
        , @NamedQuery(name = "Resident.findByMobile", query = "SELECT r FROM Resident r WHERE r.mobile = :mobile")
        , @NamedQuery(name = "Resident.findByNoofresidents", query = "SELECT r FROM Resident r WHERE r.noofresidents = :noofresidents")
        , @NamedQuery(name = "Resident.findByEnergyprovider", query = "SELECT r FROM Resident r WHERE r.energyprovider = :energyprovider")
        })


@Entity
@Table(name = "Resident")
public class Resident implements Serializable {
    private static final long serialVersionUID = 1L;
    private int resid;
    private String firstname;
    private String surname;
    private Date dob;
    private String address;
    private int postcode;
    private String email;
    private String mobile;
    private int noofresidents;
    private String energyprovider;

    @OneToMany(mappedBy = "resid")
    private Collection<Credential> credentialCollection;
    @OneToMany(mappedBy = "resid")
    private Collection<Eleusage> eleusageCollection;

    public Resident() {
    }

    public Resident(int resid1) {
        resid = resid1;
    }

    public Resident(String firstname1,  String surname1,String address1,
                    String mobile1, int noofresidents1, String energyprovider1) {
        //       this.resid = resid;
        firstname = firstname1; //
        surname = surname1;//
        address = address1;//
        mobile = mobile1;//
        noofresidents = noofresidents1;
        energyprovider = energyprovider1;//
    }


    @Id
    @NotNull
    @Column(name = "RESID")
    @GeneratedValue(strategy = GenerationType.AUTO
            //    , generator="native"
    )
    //   @GenericGenerator(name = "native", strategy = "native")
    public int getResid() {
        return resid;
    }

    public void setResid(int resid1) {
        resid = resid1;
    }

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname1) {
        firstname = firstname1;
    }

    //   @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname1) {
        surname = surname1;
    }

    @Column(name = "DOB")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="UTC")
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob1) {
        dob = dob1;
    }


    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address1) {
        address = address1;
    }

    //@Size(max = 16)
    @Column(name = "POSTCODE")
    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode1) {
        postcode = postcode1;
    }

    @Size(max = 256)
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email1) {
        email = email1;
    }

    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile1) {
        mobile = mobile1;
    }

    @NotNull
    @Column(name = "NOOFRESIDENTS")
    public int getNoofresidents() {
        return noofresidents;
    }

    public void setNoofresidents(int noofresidents1) {
        noofresidents = noofresidents1;
    }

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ENERGYPROVIDER")
    public String getEnergyprovider() {
        return energyprovider;
    }

    public void setEnergyprovider(String energyprovider1) {
        energyprovider = energyprovider1;
    }

//    @XmlTransient
    //   public Collection<Credential> getCredentialCollection() {
    //       return credentialCollection;
    //   }

//    public void setCredentialCollection(Collection<Credential> credentialCollection) {
//        this.credentialCollection = credentialCollection;
//    }

//    @XmlTransient
//    public Collection<Eleusage> getEleusageCollection() {
    //       return eleusageCollection;
    //   }

    //   public void setEleusageCollection(Collection<Eleusage> eleusageCollection) {
    //       this.eleusageCollection = eleusageCollection;
    //   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resident resident = (Resident) o;
        return resid == resident.resid &&
                noofresidents == resident.noofresidents &&
                Objects.equals(firstname, resident.firstname) &&
                Objects.equals(surname, resident.surname) &&
                Objects.equals(dob, resident.dob) &&
                Objects.equals(address, resident.address) &&
                Objects.equals(postcode, resident.postcode) &&
                Objects.equals(email, resident.email) &&
                Objects.equals(mobile, resident.mobile) &&
                Objects.equals(energyprovider, resident.energyprovider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resid, firstname, surname, dob, address, postcode, email, mobile, noofresidents, energyprovider);
    }

    @Override
    public String toString() {
        return "Resident {resId=" + resid + ", firstname=" + firstname + ", surname=" + surname + ", Dob=" + DateConverter.convertDate(dob)
                + ", Address=" + address + ", Postcode=" + postcode + ", Email=" + email + ", mobile="
                + mobile + ", NoOfResidents=" + noofresidents + ", EnergyProvider=" + energyprovider + "}";
    }
}
