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
public class hourlyusage {
    String resid;
    String address;
    String postcode;
    String fridge;
    String aircon;
    String washingmachine;
    
    public hourlyusage(){
        
    }
    
    public hourlyusage(String resid, String address, String postcode, String fridge, String aircon, String washingmachine){
        this.resid=resid;
        this.address=address;
        this.postcode=postcode;
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
    
    public String getAddress(){
        return address;
    }
    
    public void setddress(String address){
        this.address=address;
    }
    
    public String getPostcode(){
        return postcode;
    }
    
    public void setPostcode(String postcode){
        this.postcode=postcode;
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
