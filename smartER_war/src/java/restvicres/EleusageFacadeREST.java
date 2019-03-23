/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restvicres;

import entities1.HighesthourDateTime;
import entities1.Totalusage;
import entities1.Hourlyusagefordatetime;
import entities1.TotalUsageApp24H;
import utilities.SmartERUtilities;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.TypedQuery;


/**
 *
 * @author ljx
 */
@Stateless
@Path("restvicres.eleusage")
public class EleusageFacadeREST extends AbstractFacade<Eleusage> {

    @PersistenceContext(unitName = "smartERPU")
    private EntityManager em;

    public EleusageFacadeREST() {
        super(Eleusage.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Eleusage entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Eleusage entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Eleusage find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Path("findByTime/{time}")
    @Produces({"application/json"})
    public List<Eleusage> findByTime(@PathParam("time") int time) {
        Query query=em.createNamedQuery("Eleusage.findByTime");
        query.setParameter("time",time);
        return query.getResultList();
    }
    
    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Eleusage> findByDate(@PathParam("date") String datestr) throws Exception{
        List<Electricity> result = new ArrayList<Electricity>();
        try {
            Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr);   
            Query query=em.createNamedQuery("Eleusage.findByDate");        
            query.setParameter("date",date);
            result.addAll(query.getResultList()); 
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }
    
    @GET
    @Path("findByFridge/{fridge}")
    @Produces({"application/json"})
    public List<Eleusage> findByFridge(@PathParam("fridge") double fridge) {       
        Query query=em.createNamedQuery("Eleusage.findByFridge");
        query.setParameter("fridge",fridge);
        return query.getResultList();    
    }
    
    @GET
    @Path("findByAircon/{aircon}")
    @Produces({"application/json"})
    public List<Eleusage> findByAircon(@PathParam("aircon") double aircon) { 
        Query query=em.createNamedQuery("Eleusage.findByAircon");
        query.setParameter("aircon",aircon);
        return query.getResultList();           
    }  
    
    @GET
    @Path("findByWashingmachine/{washingmachine}")
    @Produces({"application/json"})
    public List<Eleusage> findByWashingmachine (@PathParam("washingmachine") double washingmachine) { 
        Query query=em.createNamedQuery("Eleusage.findByWashingmachine");
        query.setParameter("washingmachine",washingmachine);
        return query.getResultList();   
    }
    
    @GET
    @Path("findByTemperature/{temperature}")
    @Produces({"application/json"})
    public List<Eleusage> findByTemperature(@PathParam("temperature") double temperature) {
        Query query=em.createNamedQuery("Eleusage.findByTemperature");
        query.setParameter("temperature",temperature);
        return query.getResultList(); 
    }
   
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Eleusage> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Eleusage> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("findByResid/{resid}") 
    @Produces({"application/json"})
    public List<Eleusage> findByResid(@PathParam("resid") int resid) 
    {
        TypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e FROM Eleusage e WHERE e.resid.resid = :resid", Eleusage.class);
        qu.setParameter("resid", resid); 
        return qu.getResultList();
}
    
    @GET
    @Path("findByMobileAndTime/{mobile}/{time}") 
    @Produces({"application/json"})
    public List<Eleusage> findByMobileAndTime(@PathParam("mobile") String mobile, 
            @PathParam("time") int time) 
    {
        TypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e FROM Eleusage e WHERE e.resid.mobile = :mobile and e.time=:time", Eleusage.class);
        qu.setParameter("mobile", mobile); 
        qu.setParameter("time", time);
        return qu.getResultList();
}
    
    @GET
    @Path("findByEmailANDTime/{email}/{time}") 
    @Produces({"application/json"})
    public List<Eleusage> findByEmailANDTime
        (@PathParam("email") String email, @PathParam("time") int time) 
    {
        Query query = em.createNamedQuery("Eleusage.findByEmailANDTime"); 
        query.setParameter("email", email); 
        query.setParameter("time", time);
        return query.getResultList();
    }
        
    @GET
    @Path("findbyIDANDAPPANDDateANDtime/{resid}/{appliance}/{date}/{time}")
    @Produces({"application/json"})
    public String findbyIDANDAPPANDDateANDtime
    (@PathParam("resid") int resid, @PathParam("appliance") String appliance, @PathParam("date") String datestr, @PathParam("time") int time) 
            throws Exception
    {
        String result = "";
        try {
            Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr); 
            if (appliance.equalsIgnoreCase("fridge")){
                TypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e.fridge FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time", Eleusage.class);
                qu.setParameter("resid", resid); 
                qu.setParameter("date", date);
                qu.setParameter("time", time);
                result = qu.getResultList().toString();
            }
            if (appliance.equalsIgnoreCase("air conditioner")){
            T   ypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e.aircon FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time", Eleusage.class);
                qu.setParameter("resid", resid); 
                qu.setParameter("date", date);
                qu.setParameter("time", time);
                result = qu.getResultList().toString();
            }
            if (appliance.equalsIgnoreCase("washing machine")){
                TypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e.washingmachine FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time", Eleusage.class);
                qu.setParameter("resid", resid); 
                qu.setParameter("date", date);
                qu.setParameter("time", time);
                result =  qu.getResultList().toString();
        }
            result = "nothing";
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }
    
    @GET
    @Path("findbyIDANDDateANDtime/{resid}/{date}/{time}")
    @Produces({"application/json"})
    public Object findbyIDANDDateANDtime
    (@PathParam("resid") int resid, @PathParam("date") String datestr, @PathParam("time") int time) 
            throws Exception
    {
        JsonArray jArray = null;
        try {
        Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr); 
        TypedQuery<Object[]> qu = em.createQuery
        ("SELECT e.fridge,e.aircon,e.washingmachine FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time", Object[].class);
        qu.setParameter("resid", resid); 
        qu.setParameter("date", date);
        qu.setParameter("time", time);
        List<Object[]> l= qu.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : l) {
            String s1=row[0].toString();
            String s2=row[1].toString();
            String s3=row[2].toString();
            JsonObject usageObject = Json.createObjectBuilder()
                  .add("fridge", s1)
                  .add("aircon",s2)
                  .add("washingmachine",s3).build(); 
            arrayBuilder.add(usageObject);
        }
        jArray = arrayBuilder.build();
        } catch (Exception ex) {
            throw ex;
        } 
        return jArray;
    }   
    
    @GET
    @Path("findbyDateANDtimeTemp/{date}/{time}")
    @Produces({"application/json"})
    public List<Eleusage> findbyDateANDtimeTemp
    (@PathParam("date") String datestr, @PathParam("time") int time) throws Exception
    {
        List<Eleusage> results = new ArrayList<Eleusage>();
        try {
            Query query = em.createNamedQuery("Electricity.findByDateTime");
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(datestr);
            query.setParameter("date", date);
            query.setParameter("time", time);
            results = query.getResultList();
        } catch(Exception ex) {
            throw ex;
        }     
        return results;
    }
    
    @GET
    @Path("findbyDateANDtime/{date}/{time}")
    @Produces({"application/json"})
    public List<Hourlyusagefordatetime> findbyDateANDtime
    (@PathParam("date") String datestr, @PathParam("time") int time) throws Exception
    {
        List<Hourlyusagefordatetime> results = new ArrayList<Hourlyusagefordatetime>();
        try {
            List<Eleusage> usageList = findbyDateANDtimeTemp(datestr, time);
            for (Eleusage el : usageList) {
                String total = String.valueOf(el.getTotalUsage());
                String resid = String.valueOf(el.getResid().getResid());
                String address = el.getResid().getAddress();
                String postcode = el.getResid().getPostcode();
                results.add(new Hourlyusagefordatetime(resid, address, postcode, total));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return results;
    }
    
    @GET
    @Path("findhighesthour/{resid}")
    @Produces({"application/json"})
    public List<HighesthourDateTime> findhighesthour
    (@PathParam("resid") int resid) 
    {
        List<HighesthourDateTime> results = new ArrayList<>(0);
        // Find all usage by resid
        List<Eleusage> allUsages = findByResId(resid);
        // Sort the above list
        Collections.sort(allUsages);
        double highest = allUsages.get(allUsages.size() - 1).getTotalUsage();
        // Find multiple highest hours if exsit
        for (int i = allUsages.size() - 1; i >= 0 ; i--){
            Eleusage el = allUsages.get(i);
            if(highest == el.getTotalUsage()) {
                HighesthourDateTime highestInfo = new HighesthourDateTime(el.getUsagedate(), String.valueOf(el.getUsagehour()), String.valueOf(el.getTotalUsage()));
                results.add(highestInfo);
            } else if (highest > el.getTotalUsage()) {
                break;
            }
        }
        return results;        
    }
    
    @GET
    @Path("finddailyappliance/{resid}/{date}")
    @Produces({"application/json"})
    public Object finddailyappliance(@PathParam("resid") int resid, @PathParam("date") String datestr) throws Exception{
        JsonArray jArray = null;
        try {
            Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr);
            TypedQuery<Object[]> queryList = em.createQuery
        ("SELECT e.resid.resid,sum(e.fridge) as fridge,sum(e.aircon) as aircon,sum(e.washingmachine) as washingmachine FROM Eleusage e where e.resid.resid=:resid and e.date=:date group by e.resid.resid",
            Object[].class);
            queryList.setParameter("resid",resid);
            queryList.setParameter("date",date);
            List<Object[]> ql=queryList.getResultList();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
            for (Object[] row : ql) {
                String s1=row[0].toString();
                String s2=row[1].toString();
                double d2=Double.parseDouble(s2);      
                s2=df.format(d2); 
                String s3=row[2].toString();
                String s4=row[3].toString();
                JsonObject usageObject = Json.createObjectBuilder().
                    add("resid", s1)
                    .add("fridge", s2)
                    .add("aircon",s3)
                    .add("washingmachine",s4).build(); 
                arrayBuilder.add(usageObject);
            }
            jArray = arrayBuilder.build();
        } catch (Exception ex) {
            throw ex;
        }
        return jArray;
    }
    
    @GET
    @Path("finddailyorhourly/{resid}/{date}/{view}")
    @Produces({"application/json"})
    public List<Totalusage> finddailyorhourly(@PathParam("resid") int resid, @PathParam("date") String datestr,@PathParam("view") String variable) throws Exception{
        List<Totalusage> res=new ArrayList<>();
        try {
            java.text.DecimalFormat df =new java.text.DecimalFormat("#.00"); 
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            List<Eleusage> usageList = SmartERUtilities.getUsageByResidAndDate(resid, datestr, em);
            if (variable.equals("hourly")){
                for (Eleusage el : usageList)
                {
                    double total=el.getTotalUsage();
                    String strtemp=df.format(total); 
                    String temperature = String.valueOf(el.getTemperature());
                    String strDate = dateFormat.format(el.getDate());
                    String time = String.valueOf(el.getTime());;
                    res.add(new Totalusage(String.valueOf(resid),strtemp,temperature,strDate,time));
            }
        }  
        if (variable.equals("daily")){ 
            double totalusage=0.0;
            double totaltemp=0.0;
            for (Eleusage el : usageList)
            {
                double total=el.getTotalUsage();;
                totalusage = totalusage+total;
                totaltemp = totaltemp+Double.parseDouble(String.valueOf(el.getTemperature()));
            }
            double avgtemp=totaltemp/24;
            String strtemp=df.format(avgtemp); 
            String strusage=df.format(totalusage);
            res.add(new Totalusage(String.valueOf(resid),strusage,strtemp));
            return res;
        }
        res.add(new Totalusage("Please","input","dailyORhourly"));
    } catch (Exception ex) {
            throw ex;
    }
    return res;
    }
    
    @GET
    @Path("findAllEachApp24H/{resid}/{date}")
    @Produces({"application/json"})
    public TotalUsageApp24H findAllEachApp24H(@PathParam("resid") int resid, @PathParam("date") String datestr) throws Exception {
        TotalUsageApp24H result = null;
        try {
            List<Eleusage> usageList = SmartERUtilities.getUsageByResidAndDate(resid, datestr, em);      
            double fridge = SmartERTools.getFridgeTotal(usageList);
            double washingMachine = SmartERTools.getWashingmachineTotal(usageList);
            double airCon = SmartERTools.getAirconTotal(usageList);
            result = new TotalUsageApp24H(String.valueOf(resid), String.valueOf(fridge), String.valueOf(airCon), String.valueOf(washingMachine));
        } catch (Exception ex) {
            throw ex;
        }       
        return result;
    }
}
    


    

