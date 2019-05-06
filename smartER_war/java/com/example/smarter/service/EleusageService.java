package com.example.smarter.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.smarter.dao.EleusageDao;
import com.example.smarter.entity.Eleusage;
import com.example.smarter.entity.HighesthourDateTime;
import com.example.smarter.entity.Hourlyusagefordatetime;
import com.example.smarter.entity.TotalUsageApp24H;
import com.example.smarter.entity.Totalusage;

@Service("EleusageService")
public class EleusageService {
	@Autowired
	private EleusageDao eleusageDao;
	
	@Transactional
	public Eleusage findByUsageid(String usageid){
		return eleusageDao.findByUsageid(usageid);
	}
	
	@Transactional
	public List<Eleusage> findUsageByUsageid(String usageid){
		return eleusageDao.findUsageByUsageid(usageid);
	}
	
	@Transactional
	public List<Eleusage> findByResid(int resid){
		return eleusageDao.findByResid(resid);
	}
	
	@Transactional
	public List<Eleusage> findAllUsage(){
		return eleusageDao.findAllusage();
	}
	
	@Transactional
	public void createEleusage(Eleusage eleusage) {
		eleusageDao.createEleusage(eleusage);
	}
	
	@Transactional
	public List<Totalusage> finddailyorhourly(int resid, String datestr, String variable) throws Exception{
		List<Totalusage> res = new ArrayList<>();
		try {
			res = eleusageDao.finddailyorhourly(resid, datestr, variable);
			return res;
		}
		catch(Exception ex) {
			ex.getMessage();
		}
		return res;
	}
	
	@Transactional
	public TotalUsageApp24H finddailyappliance(int resid, String datestr) throws Exception{
		TotalUsageApp24H res = null;
		try {
			res = eleusageDao.finddailyappliance(resid, datestr);
	//		return res;
		}
		catch(Exception ex) {
			throw ex;
		}
	    return res;
	}
	
	@Transactional
	public List<Eleusage> findbyIDANDDateANDtime(int resid, String datestr, int time) throws Exception{
		List<Eleusage> result = new ArrayList<Eleusage>();
		try {
			result = eleusageDao.findUsageByResidDateTime(resid, datestr, time);
		}
		catch(Exception ex) {
			throw ex;
		}
	    return result;
	}
	
	@Transactional
	public List<Hourlyusagefordatetime> findbyDateANDtime(String datestr, int time) throws Exception{
		List<Hourlyusagefordatetime> results = new ArrayList<Hourlyusagefordatetime>();
        try {
        	results=eleusageDao.findbyDateANDtime(datestr, time);
        }
        catch(Exception ex) {
			throw ex;
		}
	    return results;
	}
	
	@Transactional
	public List<HighesthourDateTime> findhighesthour(int resid){
		return eleusageDao.findhighesthourAlt(resid);
	}
	
	@Transactional
	public void updateEleusage(Eleusage eleusage) {
		eleusageDao.updateEleusage(eleusage);
	}
	
	@Transactional
	public void removeEleusage(String usageid) {
		eleusageDao.removeEleusage(usageid);
	}
	
}
