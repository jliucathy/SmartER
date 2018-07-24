package com.example.ljx.smarter;

import android.app.Application;

public class LoginUserRetrivals extends Application {
    private String userInfo;
    public static String r_firstname;
    public static String resid;
    public static String l_firstname;
    public static String postcode;

    public LoginUserRetrivals(){

    }

    public static void setRegisterUser(String register){
        r_firstname=register;
    }

    public static String getRegisterUser(){
        return r_firstname;
    }

    public static void setResid(String userid){
        resid=userid;
    }

    public static String getResid(){
        return resid;
    }

    public static void setLogFirstname(String firstname){
        l_firstname=firstname;
    }

    public static String getLogFirstname(){
        return l_firstname;
    }

    public static void setPostcode(String postcode1){
        postcode=postcode1;
    }

    public static String getPostcode(){
        return postcode;
    }



    //findbyDateANDtime map hourly (24)
    //findbyresid---%
}
