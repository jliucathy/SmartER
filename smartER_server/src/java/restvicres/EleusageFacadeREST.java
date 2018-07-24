/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restvicres;

import entities1.Highesthour;
import entities1.HighesthourDateTime;
import entities1.Hourlytotal;
import entities1.Totalusage;
import entities1.hourlyusage;
import entities1.hourlyusagefordatetime;
import java.util.Date;
import java.util.List;
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
        Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr);   
        Query query=em.createNamedQuery("Eleusage.findByDate");        
        query.setParameter("date",date);
        return query.getResultList();     
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
        Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr); 
        if (appliance.equalsIgnoreCase("fridge")){
            TypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e.fridge FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time", Eleusage.class);
            qu.setParameter("resid", resid); 
            qu.setParameter("date", date);
            qu.setParameter("time", time);
            return qu.getResultList().toString();
        }
        if (appliance.equalsIgnoreCase("air conditioner")){
            TypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e.aircon FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time", Eleusage.class);
            qu.setParameter("resid", resid); 
            qu.setParameter("date", date);
            qu.setParameter("time", time);
            return qu.getResultList().toString();
        }
        if (appliance.equalsIgnoreCase("washing machine")){
            TypedQuery<Eleusage> qu = em.createQuery
        ("SELECT e.washingmachine FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date and e.time=:time", Eleusage.class);
            qu.setParameter("resid", resid); 
            qu.setParameter("date", date);
            qu.setParameter("time", time);
            return qu.getResultList().toString();
        }
        return "nothing";
        }
    
    @GET
    @Path("findbyIDANDDateANDtime/{resid}/{date}/{time}")
    @Produces({"application/json"})
    public Object findbyIDANDDateANDtime
    (@PathParam("resid") int resid, @PathParam("date") String datestr, @PathParam("time") int time) 
            throws Exception
    {
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
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
       
    
    @GET
    @Path("findbyDateANDtime/{date}/{time}")
    @Produces({"application/json"})
    public List<hourlyusagefordatetime> findbyDateANDtime
    (@PathParam("date") String datestr, @PathParam("time") int time) throws Exception
    {
        Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr); 
        TypedQuery<Object[]> qu = em.createQuery
        ("SELECT e.resid,e.resid.address,e.resid.postcode,e.fridge,e.aircon,e.washingmachine FROM Eleusage e WHERE e.date=:date and e.time=:time", Object[].class);
        qu.setParameter("date", date);
        qu.setParameter("time", time);
        List<Object[]> ql=qu.getResultList();  
        List<hourlyusage> templist=new ArrayList<>();
        for (Object[] row : ql) {
            String s1=row[0].toString();
            String s2=row[1].toString();
            String s3=row[2].toString();
            String s4=row[3].toString();
            String s5=row[4].toString();
            String s6=row[5].toString();
            templist.add(new hourlyusage(s1,s2,s3,s4,s5,s6));
        }
        List<hourlyusagefordatetime> res=new ArrayList();
        for (hourlyusage h:templist){
            double fri=Double.parseDouble(h.getFridge());
            double air=Double.parseDouble(h.getAircon());
            double was=Double.parseDouble(h.getWashingmachine());
            double total=fri+air+was;
            java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
            String strtemp=df.format(total);    
            res.add(new hourlyusagefordatetime(h.getResid(),h.getAddress(),h.getPostcode(),String.valueOf(strtemp)));
        }       
        return res;        
    }
    
    @GET
    @Path("findhighesthour/{resid}")
    @Produces({"application/json"})
    public List<HighesthourDateTime> findhighesthour
    (@PathParam("resid") int resid) 
    {
        TypedQuery<Object[]> qu = em.createQuery
        ("SELECT e.resid.resid,e.date,e.time,e.fridge,e.aircon,e.washingmachine FROM Eleusage e WHERE e.resid.resid=:resid", Object[].class);
        qu.setParameter("resid", resid);
        List<Object[]> ql=qu.getResultList();  
        List<Highesthour> templist=new ArrayList<>();
        for (Object[] row : ql) {
            String s1=row[0].toString();
            String s2=row[1].toString();
            String s3=row[2].toString();
            String s4=row[3].toString();
            String s5=row[4].toString();
            String s6=row[5].toString();
            templist.add(new Highesthour(s1,s2,s3,s4,s5,s6));
        }
        List<HighesthourDateTime> res=new ArrayList();
        double highest=0.0;
        ArrayList<Highesthour> newh=new ArrayList<>();
        for (Highesthour h:templist){
            double fri=Double.parseDouble(h.getFridge());
            double air=Double.parseDouble(h.getAircon());
            double was=Double.parseDouble(h.getWashingmachine());
            double total=fri+air+was;
            if (total>highest){
                highest=total;
            }
        }
        for (Highesthour h:templist){
            double fri=Double.parseDouble(h.getFridge());
            double air=Double.parseDouble(h.getAircon());
            double was=Double.parseDouble(h.getWashingmachine());
            double total=fri+air+was;
            if (total==highest){
                newh.add(h);
            }
        }
        for (Highesthour h:newh){
            double fri=Double.parseDouble(h.getFridge());
            double air=Double.parseDouble(h.getAircon());
            double was=Double.parseDouble(h.getWashingmachine());
            double total=fri+air+was;
            java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
            String strtemp=df.format(total);  
            res.add(new HighesthourDateTime(h.getResid(),h.getDate(),h.getTime(),strtemp));   
        }
        return res;        
    }
    
    @GET
    @Path("finddailyappliance/{resid}/{date}")
    @Produces({"application/json"})
    public Object finddailyappliance(@PathParam("resid") int resid, @PathParam("date") String datestr) throws Exception{
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
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    
    @GET
    @Path("finddailyorhourly/{resid}/{date}/{view}")
    @Produces({"application/json"})
    public List<Totalusage> finddailyorhourly(@PathParam("resid") int resid, @PathParam("date") String datestr,@PathParam("view") String variable) throws Exception{
        Date date=new SimpleDateFormat("yyyy-mm-dd").parse(datestr); 
        TypedQuery<Object[]> qu = em.createQuery
        ("SELECT e.resid.resid,e.fridge,e.aircon,e.washingmachine,e.temperature,"
                + "e.date,e.time FROM Eleusage e WHERE e.resid.resid = :resid and e.date=:date",Object[].class);
        qu.setParameter("resid", resid);
        qu.setParameter("date", date);
        List<Object[]> ql=qu.getResultList();  
        List<Hourlytotal> templist=new ArrayList<>();
        java.text.DecimalFormat df =new java.text.DecimalFormat("#.00"); 
        for (Object[] row : ql) {
            String s1=row[0].toString();
            String s2=row[1].toString();
            String s3=row[2].toString();
            String s4=row[3].toString();
            String s5=row[4].toString();
            String s6=row[5].toString();
            String s7=row[6].toString();
            templist.add(new Hourlytotal(s1,s2,s3,s4,s5,s6,s7));
        }
        List<Totalusage> res=new ArrayList<>();
        if (variable.equals("hourly")){
            for (Hourlytotal h:templist)
            {
                double fri=Double.parseDouble(h.getFridge());
                double air=Double.parseDouble(h.getAircon());
                double was=Double.parseDouble(h.getWashingmachine());
                double total=fri+air+was;
                String strtemp=df.format(total);  
                res.add(new Totalusage(h.getResid(),strtemp,h.getTemperature(),h.getDate(),h.getTime()));
            }
            return res;
        }  
        if (variable.equals("daily")){ 
            double totalusage=0.0;
            double totaltemp=0.0;
            for (Hourlytotal h:templist)
            {
                double fri=Double.parseDouble(h.getFridge());
                double air=Double.parseDouble(h.getAircon());
                double was=Double.parseDouble(h.getWashingmachine());
                double total=fri+air+was;
                totalusage = totalusage+total;
                totaltemp = totaltemp+Double.parseDouble(h.getTemperature());
            }
            double avgtemp=totaltemp/24;
            String strtemp=df.format(avgtemp); 
            String strusage=df.format(totalusage);
            res.add(new Totalusage(String.valueOf(resid),strusage,strtemp));
            return res;
        }
        List<Totalusage> lt=new ArrayList<>();
        lt.add(new Totalusage("Please","input","dailyORhourly"));
        return lt;
    }
    
    }
    


    

