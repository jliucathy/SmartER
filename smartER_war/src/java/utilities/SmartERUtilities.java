package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.DecimalFormat;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import restvicres.Eleusage;

/**
 * @author ljx
 */
public class SmartERUtilities {
    
    public static double getFridgeTotal(List<Eleusage> list) {
        double result = 0.0;
        for (Eleusage el : list) {
            result += el.getFridge().doubleValue();
        }
        java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
        String strtemp=df.format(result); 
        double total = Double.parseDouble(strtemp);
        return total;
    }
    
    public static double getAirconTotal(List<Eleusage> list) {
        double result = 0.0;
        for (Eleusage el : list) {
            result += el.getAircon().doubleValue();
        }
        java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
        String strtemp=df.format(result); 
        double total = Double.parseDouble(strtemp);
        return total;
    }
    
    public static double getWashingmachineTotal(List<Eleusage> list) {
        double result = 0.0;
        for (Eleusage el : list) {
            result += el.getWashingmachine().doubleValue();
        }
        java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
        String strtemp=df.format(result); 
        double total = Double.parseDouble(strtemp);
        return total;
    }
    
    // get usage by resid and date
    public static List<Eleusage> getUsageByResidAndDate(int resid, String datestr, EntityManager em) throws Exception{
        List<Eleusage> usageList = null;
        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(datestr);
            // Find all usage data on the specific date for this resident
            Query query = em.createNamedQuery("Eleusage.findByResidDate");
            query.setParameter("resid", resid);
            query.setParameter("date", date);
            usageList = query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        return usageList;
    }

    // get total usage for all residents by date
    public static List<Eleusage> getUsageByDateForAll(String datestr, EntityManager em) throws Exception {
        List<Eleusage> usageList = null;
        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(datestr);
            // Find all usage data on the specific date for this resident
            Query query = em.createNamedQuery("Eleusage.findByDate");
            query.setParameter("date", date);
            usageList = query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        return usageList;
    }
    
}