package com.example.smarter.service;

import java.util.List;

import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.smarter.dao.ResidentDao;
import com.example.smarter.entity.Resident;

@Service("ResidentService")
public class ResidentService {
	@Autowired
//    @Qualifier("ResidentDao")
	private ResidentDao residentDao;
	
	@Transactional
	public Resident getResiddentById(int resid) {
		return residentDao.selectResidentById(resid);
	}
	
	@Transactional
	public void createResident(Resident resident) {
		residentDao.createResident(resident);
	}
	
	@Transactional
    public void updateResident(Resident resident) {
        residentDao.updateResident(resident);
    }
	
	@Transactional
	public List<Resident> findAllResident(){
		return residentDao.findAllResident();
	}
	
	@Transactional
	public List<Resident> findByEnergyprovider(String energyProvider){
		return residentDao.findByEnergyprovider(energyProvider);
	}
	
	@Transactional
	public List<Resident> findByFirstnameANDSurname(String firstname, String surname){
		return residentDao.findByFirstnameANDSurname(firstname, surname);
	}	
	
	@Transactional
	public void removeResident(int resid) {
		residentDao.removeResident(resid);
	}
	
}
