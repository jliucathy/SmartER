package com.example.smarter.dao;

import java.util.Date;
import java.util.List;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
//import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.smarter.entity.HighesthourDateTime;
import com.example.smarter.entity.Totalusage;
import com.example.smarter.entity.Hourlyusagefordatetime;
import com.example.smarter.entity.TotalUsageApp24H;
import com.example.smarter.entity.Eleusage;
import com.example.smarter.util.SmartERUtilities;

@Repository("EleusageDao")
public class EleusageDao {
	@PersistenceContext
    private EntityManager entityManager;
	
	public void createEleusage(Eleusage eleusage) {
		entityManager.persist(eleusage);
	}
	
	public List<Eleusage> findAllusage(){
		Query query=entityManager.createNamedQuery("Eleusage.findAll");
		return query.getResultList();
	}
	
	public List<Totalusage> finddailyorhourly(int resid, String datestr, String variable)
			throws Exception
	{
		List<Totalusage> res=new ArrayList<>();
        try {
            java.text.DecimalFormat df =new java.text.DecimalFormat("#.00"); 
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<Eleusage> usageList = SmartERUtilities.getUsageByResidAndDate(resid, datestr, entityManager);
            if (variable.equalsIgnoreCase("hourly")){
                for (Eleusage el : usageList)
                {
                    double total=el.retrieveTotalUsage();
                    String strtemp=df.format(total); 
                    String temperature = String.valueOf(el.getTemperature());
                    String strDate = dateFormat.format(el.getDate());
                    String time = String.valueOf(el.getTime());;
                    res.add(new Totalusage(String.valueOf(resid),strtemp,temperature,strDate,time));
            }
        }  
        if (variable.equalsIgnoreCase("daily")){ 
            double totalusage=0.0;
            double totaltemp=0.0;
            for (Eleusage el : usageList)
            {
                double total=el.retrieveTotalUsage();
                totalusage = totalusage+total;
                totaltemp = totaltemp+Double.parseDouble(String.valueOf(el.getTemperature()));
            }
            double avgtemp=totaltemp/usageList.size();
            String strtemp=df.format(avgtemp); 
            String strusage=df.format(totalusage);
            res.add(new Totalusage(String.valueOf(resid),strusage,strtemp
 //          		,datestr,"0-23"
            		));
            return res;
        }
   //    res.add(new Totalusage("Please","input","dailyORhourly")); //for test purpose
    } catch (Exception ex) {
            ex.getMessage();
    }
        return res;
	}
	
	public TotalUsageApp24H finddailyappliance(int resid, String datestr) throws Exception{
		TotalUsageApp24H result = null;
		try {
			List<Eleusage> usageList=SmartERUtilities.getUsageByResidAndDate(resid, datestr, entityManager);
			double fridgeUsage = SmartERUtilities.getFridgeTotal(usageList);			
			double airconUsage = SmartERUtilities.getAirconTotal(usageList);
			double washingmachineUsage = SmartERUtilities.getWashingmachineTotal(usageList);
			result = new TotalUsageApp24H(String.valueOf(resid), String.valueOf(fridgeUsage),
					String.valueOf(airconUsage),String.valueOf(washingmachineUsage));
			
        } catch (Exception ex) {
            throw ex;
        }
        return result;
	}
	
	public List<Eleusage> findUsageByResidDateTime(int resid, String datestr, int time) throws Exception{
		List<Eleusage> result = new ArrayList<Eleusage>();
		try {
			Date date=new SimpleDateFormat("yyyy-MM-dd").parse(datestr); 
			Query query=entityManager.createNamedQuery("Eleusage.findByResidDateTime");
			query.setParameter("resid", resid);
			query.setParameter("date", date);
			query.setParameter("time", time);
			result = query.getResultList();
		} catch (Exception ex) {
            throw ex;
        }
        return result;
	}
	
	private List<Eleusage> findbyDateANDtimeTemp(String datestr, int time) throws Exception{
		List<Eleusage> results = new ArrayList<Eleusage>();
        try {
            Query query = entityManager.createNamedQuery("Eleusage.findByDateTime");
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
            query.setParameter("date", date);
            query.setParameter("time", time);
            results = query.getResultList();
        } catch(Exception ex) {
            throw ex;
        }     
        return results;
	}
	
	public List<Hourlyusagefordatetime> findbyDateANDtime(String datestr, int time) throws Exception{
		List<Hourlyusagefordatetime> results = new ArrayList<Hourlyusagefordatetime>();
        try {
            List<Eleusage> usageList = findbyDateANDtimeTemp(datestr, time);
            for (Eleusage el : usageList) {
                String total = String.valueOf(el.retrieveTotalUsage());
                String resid = String.valueOf(el.getResid().getResid());
                String address = el.getResid().getAddress();
                String postcode = String.valueOf(el.getResid().getPostcode());
                results.add(new Hourlyusagefordatetime(resid, address, postcode, total));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return results;
	}
	
	public List<HighesthourDateTime> findhighesthour(int resid){
		List<HighesthourDateTime> results = new ArrayList<>(0);
        List<Eleusage> allUsages = findByResid(resid);
        Collections.sort(allUsages,Eleusage.totalUsageCom);
        double highest = allUsages.get(allUsages.size() - 1).retrieveTotalUsage();
        // Find multiple highest hours if exist
        for (int i = allUsages.size() - 1; i >= 0 ; i--){
            Eleusage el = allUsages.get(i);
            if(highest == el.retrieveTotalUsage()) {
                HighesthourDateTime highestInfo = new HighesthourDateTime(el.getDate(), String.valueOf(el.getTime()), String.valueOf(el.retrieveTotalUsage()));
                results.add(highestInfo);
            } else if (highest > el.retrieveTotalUsage()) {
                break;
            }
        }
        return results;    
	}
	
	public List<HighesthourDateTime> findhighesthourAlt(int resid){
		List<HighesthourDateTime> results = new ArrayList<>(0);
        List<Eleusage> allUsages = findByResid(resid);
        double highest = 0.0;
        for (Eleusage el : allUsages) {
        	double totalUse = el.retrieveTotalUsage();
        	if(totalUse>highest) {
        		highest=totalUse;       		
        	}
        }
        for (Eleusage el : allUsages) {
        	if (el.retrieveTotalUsage()==highest) {
        		HighesthourDateTime highestInfo = new HighesthourDateTime(el.getDate(), String.valueOf(el.getTime()), String.valueOf(el.retrieveTotalUsage()));
                results.add(highestInfo);
        	}
        }
        return results;    
	}
	
	public String appHourUsageOfResid(int resid, String datestr, int time, String app) throws Exception{
		String result = "";
        try {
            List<Eleusage> foundRecords = findUsageByResidDateTime(resid, datestr, time);
            if (foundRecords.size() > 0) {
                Eleusage el = foundRecords.get(0);
                switch (app){
                    case "fridge":
                        result = Double.toString(el.getFridge().doubleValue());
                        break;
                    case "aircon":
                        result = Double.toString(el.getAircon().doubleValue());
                        break;
                    case "washingmachine":
                        result = Double.toString(el.getWashingmachine().doubleValue());
                        break;
                    default:
                        result = "";
                        break;
                }                        
            } else {
                result = "";
            }
        } catch (Exception ex) {
            throw ex;
        }
        return result;
	}
	
	public String findSumUsage(int resid, String datestr, int time) throws Exception{
		String result = "";
        try {
            List<Eleusage> foundRecords = findUsageByResidDateTime(resid, datestr,time);
            if (foundRecords.size() > 0) {
                Eleusage el = foundRecords.get(0);
                result = Double.toString(el.retrieveTotalUsage());
            } else {
                result = "";
            }
        } catch (Exception ex) {
            throw ex;
        }        
        return result;
	}
	
	public List<Eleusage> findByResid(int resid){
		TypedQuery<Eleusage> qu=entityManager.createQuery
				("SELECT e from Eleusage e Where e.resid.resid = :resid", Eleusage.class);
		qu.setParameter("resid", resid); 
        return qu.getResultList();
	}
	
	public List<Eleusage> findUsageByUsageid(String usageid){
		Query query=entityManager.createNamedQuery("Eleusage.findByUsageid");
		query.setParameter("usageid", usageid);
		return query.getResultList();
	}
	
	public Eleusage findByUsageid(String usageid) {
		Query query=entityManager.createNamedQuery("Eleusage.findByUsageid");
		query.setParameter("usageid", usageid);
		return (Eleusage) query.getResultList().get(0);
	}
	
	public void updateEleusage(Eleusage eleusage) {
		Eleusage usageToUpdate = findByUsageid(eleusage.getUsageid());
		usageToUpdate.setAircon(eleusage.getAircon());
		usageToUpdate.setDate(eleusage.getDate());
		usageToUpdate.setFridge(eleusage.getFridge());
		usageToUpdate.setResid(eleusage.getResid());
		usageToUpdate.setTemperature(eleusage.getTemperature());
		usageToUpdate.setTime(eleusage.getTime());
		usageToUpdate.setWashingmachine(eleusage.getWashingmachine());
		entityManager.flush();
	}
	
	public void removeEleusage(String usageid) {
		entityManager.remove(findByUsageid(usageid));
	}
}
