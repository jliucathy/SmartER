package com.example.smarter.entity;

public class TotalUsageApp24H {
	String resid;
    String fridge;
    String aircon;
    String washingmachine;

    public TotalUsageApp24H(){

    }

    public TotalUsageApp24H(String resid, String fridge, String aircon, String washingmachine){
        this.resid=resid;
        this.fridge=fridge;
        this.aircon=aircon;
        this.washingmachine=washingmachine;

    }

    public String getResid(){
        return resid;
    }

    public void setResid(String resid){
        this.resid=resid;
    }

    public String getFridge(){
        return fridge;
    }

    public void setFridge(String fridge){
        this.fridge=fridge;
    }

    public String getAircon(){
        return aircon;
    }

    public void setAircon(String aircon){
        this.aircon=aircon;
    }

    public String getWashingmachine(){
        return washingmachine;
    }

    public void setWashingmachine(String washingmachine){
        this.washingmachine=washingmachine;
    }
}
