package com.example.smarter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.smarter.service.EleusageService;
import com.example.smarter.entity.Eleusage;
import com.example.smarter.entity.HighesthourDateTime;
import com.example.smarter.entity.Hourlyusagefordatetime;
import com.example.smarter.entity.TotalUsageApp24H;
import com.example.smarter.entity.Totalusage;

@RestController
public class EleusageController {
	@Autowired
	private EleusageService eleusageService;
	
	@PostMapping("/api/createEleusage")
	public void createEleusage(@Valid @RequestBody Eleusage eleusage) {
		eleusageService.createEleusage(eleusage);
	}
	
	@GetMapping("/api/findUsageByUsageid/{usageid}")
	public List<Eleusage> findUsageByUsageid(@PathVariable(value ="usageid") String usageid){
		return eleusageService.findUsageByUsageid(usageid);
	}
	
	@GetMapping("/api/findUsageByResid/{resid}")
	public List<Eleusage> findUsageByResid(@PathVariable(value ="resid") int resid){
		return eleusageService.findByResid(resid);
	}
	
	@GetMapping("/api/findAllUsage")
	public List<Eleusage> findAllUsage(){
		return eleusageService.findAllUsage();
	}
	
	@GetMapping("/api/finddailyorhourly/{resid}/{datestr}/{variable}")
	public List<Totalusage> finddailyorhourly(@PathVariable(value ="resid") int resid, 
			@PathVariable(value ="datestr") String datestr, @PathVariable(value ="variable") String variable) 
					throws Exception
	{
		List<Totalusage> res = new ArrayList<>();
		try {
			res = eleusageService.finddailyorhourly(resid, datestr, variable);
			return res;
		}
		catch(Exception ex) {
			ex.getMessage();
		}
		return res;
	}
	
	@GetMapping("/api/finddailyappliance/{resid}/{datestr}")
	public TotalUsageApp24H finddailyappliance(@PathVariable(value ="resid") int resid, @PathVariable(value ="datestr") String datestr) 
		 throws Exception
	{
		TotalUsageApp24H res = null;
		try {
			res = eleusageService.finddailyappliance(resid, datestr);
			return res;
		}
		catch(Exception ex) {
			throw ex;
		}
	//	return res;
	}
	
	@GetMapping("/api/findbyIDANDDateANDtime/{resid}/{date}/{time}")
	public List<Eleusage> findbyIDANDDateANDtime(@PathVariable(value ="resid") int resid, @PathVariable(value ="date") String datestr, 
			@PathVariable(value ="time") int time) 
					throws Exception
	{
		List<Eleusage> result = new ArrayList<Eleusage>();
		try {
			result = eleusageService.findbyIDANDDateANDtime(resid, datestr, time);			
		}
		catch(Exception ex) {
			throw ex;
		}
	    return result;
	}
	
	@GetMapping("/api/findbyDateANDtime/{date}/{time}")
	public List<Hourlyusagefordatetime> findbyDateANDtime(@PathVariable(value ="date") String datestr, @PathVariable(value ="time") int time) throws Exception{
		List<Hourlyusagefordatetime> results = new ArrayList<Hourlyusagefordatetime>();
        try {
        	results=eleusageService.findbyDateANDtime(datestr, time);
        }
        catch(Exception ex) {
			throw ex;
		}
	    return results;
	}
	
	@GetMapping("/api/findhighesthour/{resid}")
	public List<HighesthourDateTime> findhighesthour(@PathVariable(value ="resid") int resid){	
		return eleusageService.findhighesthour(resid);
	}
	
	@PutMapping("/api/updateEleusage")
	public void updateEleusage(@Valid @RequestBody Eleusage eleusage) {
		eleusageService.updateEleusage(eleusage);
	}
	
	@DeleteMapping("/api/removeEleusage/{usageid}")
	public void removeEleusage(@PathVariable(value ="usageid") String usageid) {
		eleusageService.removeEleusage(usageid);
	}
		
}
