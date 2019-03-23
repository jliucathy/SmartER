/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities1;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author ljx
 */
@XmlRootElement
public class Hourlytotal {
    String resid;
    String fridge;
    String aircon;
    String washingmachine;
    String temperature;
    String date;
    String time;
    
    public Hourlytotal(){
        
    }
    
    public Hourlytotal(String resid, String fridge, String aircon, String washingmachine, String temperature, String date, String time){
        this.resid=resid;
        this.fridge=fridge;
        this.aircon=aircon;
        this.washingmachine=washingmachine;
        this.temperature=temperature;
        this.date=date;
        this.time=time;
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
    
    public String getTemperature(){
        return temperature;
    }
    
    public void setTemperature(String temperature){
        this.temperature=temperature;
    }
    
    public String getDate(){
        return date;
    }
    
    public void setDate(String date){
        this.date=date;
    }
    
    public String getTime(){
        return time;
    }
    
    public void setTime(String time){
        this.time=time;
    }
    
}
