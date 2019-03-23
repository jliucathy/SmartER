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
public class Hourlyusagefordatetime {
    String resid;
    String address;
    String postcode;
    String totalusage;
    
    public Hourlyusagefordatetime() {
    }
    
    public Hourlyusagefordatetime(String resid, String address, String postcode, String totalusage) {
        this.resid=resid;
        this.address=address;
        this.postcode=postcode;
        this.totalusage=totalusage;      
    }

    public String getResid() {
        return resid;
    }
    
    public void setResid(String resid) {
        this.resid = resid;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPostcode() {
        return postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    public String getTotalusage() {
        return totalusage;
    }
    
    public void setTotalusage(String totalusage) {
        this.totalusage = totalusage;
    }
    
    
    
    
}
