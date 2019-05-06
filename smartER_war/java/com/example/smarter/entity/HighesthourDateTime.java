package com.example.smarter.entity;

import java.util.Date;

public class HighesthourDateTime {
	Date date;
    String time;
    String total;

    public HighesthourDateTime(){

    }

    public HighesthourDateTime(Date date, String time, String total){
        this.date=date;
        this.time=time;
        this.total=total;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
