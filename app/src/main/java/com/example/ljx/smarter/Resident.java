package com.example.ljx.smarter;

public class Resident {
    private String resid;
    private String firstname;
    private String surname;
    private String dob;
    private String address;
    private String postcode;
    private String email;
    private String mobile;
    private String noofresidents;
    private String energyprovider;


    public Resident(String resid)
    {
        this.resid=resid;
    }

    public Resident(String resid, String firstname, String surname, String dob, String address, String postcode, String email,
            String mobile, String noofresidents, String energyprovider){
        this.resid=resid;
        this.firstname=firstname;
        this.surname=surname;
        this.dob=dob;
        this.address=address;
        this.postcode=postcode;
        this.email=email;
        this.mobile=mobile;
        this.noofresidents=noofresidents;
        this.energyprovider=energyprovider;
    }

    public String getResid(){
        return resid;
    }

    public String getFirstname(){
        return firstname;
    }
    public String getSurname(){
        return surname;
    }
    public String getAddress(){
        return address;
    }
    public String getPostcode(){
        return postcode;
    }
    public String getEmail(){
        return email;
    }

    public String getMobile(){
        return mobile;
    }


}
