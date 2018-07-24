package com.example.ljx.smarter;

import android.os.AsyncTask;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by jliu0030 on 24/4/18.
 */

public class EleusageSimulator {

    public static int counter=0;
    public static int flag=0;
    private int timeRecord;
    private int times;
    private String usageid;
    private Resident resid;
    private String date;
    private String time;
    private String temperature;
    private String aircon;
    private String fridge;
    private String washingmachine;
    private ArrayList<String> washing=new ArrayList<>();

    public EleusageSimulator(){}

    public EleusageSimulator(String usageid, String resid, String date, String time, String temperature,
                             String aircon, String washingmachine, String fridge){
        this.usageid=usageid;
        this.resid=new Resident(resid);
        this.date=date;
        this.time=time;
        this.temperature=temperature;
        this.aircon=aircon;
        this.washingmachine=washingmachine;
        this.fridge=fridge;
    }

    public void setResid(String resid){
        this.resid=new Resident(resid);
    }

    public String getResid(){
        return resid.getResid();
    }

    public String genDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String tempDate=dateFormat.format(cal.getTime());
        date=tempDate.substring(0,11)+"00:00:00+11:00";
        return date;
    }

    private int getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String temp1=dateFormat.format(cal.getTime());
        int currentTime=Integer.parseInt(temp1.substring(11,13));
        return currentTime;
    }

    public String getTime(){
        time=String.valueOf(getCurrentTime());
        return time;
    }

    public String genUsageid(String time){
        String tempdate1=date.substring(0,4);
        String tempdate2=date.substring(5,7);
        String tempdate3=date.substring(8,10);
        this.usageid=resid+tempdate1+tempdate2+tempdate3+time;
        return usageid;
    }

    public void setTemperature(String temperature){
        this.temperature=temperature;
    }

    public String getTemperature(){
        return temperature;
    }

    public String genFridge() {
        Random r = new Random();
        double leftfridge = 0.3;
        double rightfridge = 0.8;
        double tempfridge = leftfridge + r.nextDouble() * (rightfridge - leftfridge);
        DecimalFormat df = new DecimalFormat("##0.0");
        fridge = df.format(tempfridge);
        return fridge;
    }

    public String genAircon(String temp){
        Double tem=Double.parseDouble(temp);
        if(counter>=0
                && counter<=9
                && getCurrentTime()>=9
                && getCurrentTime()<23
                && tem>20.0){
            Random r = new Random();
            double tempaircon = r.nextDouble() * 4 + 1;
            DecimalFormat df = new DecimalFormat("##0.0");
            aircon = df.format(tempaircon);
            counter++;
        }
        else{
            aircon=String.valueOf(0.0);
        }
        return aircon;
    }

    public void genWashingmachine (){
        if(flag<1
                && getCurrentTime()>=6
                && getCurrentTime()<=18) {
            Random r = new Random();
            double leftwash = 0.4;
            double rightwash = 1.3;
            double tempwashingmachine = leftwash + r.nextDouble() * (rightwash - leftwash);
            times=1+r.nextInt(3);
            int i=1;
            if(times==1){
                DecimalFormat df = new DecimalFormat("##0.0");
                washingmachine= df.format(tempwashingmachine);
            }
            while(i<=times && times!=1){
                double tempwashing = leftwash + r.nextDouble() * (rightwash - leftwash);
                DecimalFormat df = new DecimalFormat("##0.0");
                String tempwash= df.format(tempwashing);
                washing.add(tempwash);
                i+=1;
            }
            timeRecord=getCurrentTime();
            flag++;
        }
        else{
            washingmachine="0.0";
        }
    }

    public String getWashingmachine(){
        if (getCurrentTime()>=6
                && getCurrentTime()<21
                ) {
            if (times == 1) {
                return washingmachine;
            }
            if (times == 2) {
                if(getCurrentTime()==timeRecord){
                    return washing.get(0);
                }
                if (getCurrentTime() == timeRecord + 1) {
                    return washing.get(1);
                }
            }
            if (times == 3) {
                if(getCurrentTime()==timeRecord){
                    return washing.get(0);
                }
                if (getCurrentTime() == timeRecord + 1) {
                    return washing.get(1);
                }
                if (getCurrentTime() == timeRecord + 2) {
                    return washing.get(2);
                }
            }
        }
        return String.valueOf(0.0);
    }

    public static int getCounter(){
        return counter;
    }

    public static int getflag(){
        return flag;
    }
}



