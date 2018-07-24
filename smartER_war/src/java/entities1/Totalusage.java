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
public class Totalusage {
    String resid;
    String totalusage;
    String temperature;
    String date;
    String time;
    
    public Totalusage(){
        
    }
    
    public Totalusage(String resid, String totalusage, String temperature){
        this.resid=resid;
        this.totalusage=totalusage;
        this.temperature=temperature;
    }
    
    public Totalusage(String resid, String totalusage, String temperature, String date, String time){
        this.resid=resid;
        this.totalusage=totalusage;
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
    
    public String getTotalusage(){
        return totalusage;
    }
    
    public void setTotalusage(String totalusage){
        this.totalusage=totalusage;
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
