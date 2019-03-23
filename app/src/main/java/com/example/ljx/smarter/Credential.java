package com.example.ljx.smarter;

public class Credential {
    private String username;
    private String password;
    private Resident resid;
    private String registrationdate;


    public Credential(){

    }

    public Credential(String username, String password, String resid, String registrationDate){
        this.username=username;
        this.password=password;
        this.resid=new Resident(resid);
        this.registrationdate=registrationDate;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
