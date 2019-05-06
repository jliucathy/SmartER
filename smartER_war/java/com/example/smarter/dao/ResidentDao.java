package com.example.smarter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
 
import org.springframework.stereotype.Repository;

import com.example.smarter.entity.Resident;

@Repository("ResidentDao")
public class ResidentDao {
	@PersistenceContext
    private EntityManager entityManager;

	
	public Resident selectResidentById(int resid) {
		return entityManager.find(Resident.class, resid);
	}
	
	public void createResident(Resident resident) {
		entityManager.persist(resident);
	}
	
	public void updateResident(Resident resident)
	{
		Resident residentToUpdate = selectResidentById(resident.getResid());
		residentToUpdate.setFirstname(resident.getFirstname());
		residentToUpdate.setSurname(resident.getSurname());
		residentToUpdate.setDob(resident.getDob());
		residentToUpdate.setAddress(resident.getAddress());
		residentToUpdate.setPostcode(resident.getPostcode());
		residentToUpdate.setEmail(resident.getEmail());
		residentToUpdate.setMobile(resident.getMobile());
		residentToUpdate.setNoofresidents(resident.getNoofresidents());
		residentToUpdate.setEnergyprovider(resident.getEnergyprovider());
		entityManager.flush();
	}
	
	public void removeResident(int resid) {
		entityManager.remove(selectResidentById(resid));
	}
	
	public List<Resident> findAllResident(){
		TypedQuery<Resident> query = entityManager.createQuery("select r from Resident r order by r.resid",Resident.class);
		return query.getResultList();
	}
	
	public List<Resident> findByEnergyprovider(String energyProvider){
		Query qu=entityManager.createNamedQuery("Resident.findByEnergyprovider");
		qu.setParameter("energyprovider", energyProvider);
		return (List<Resident>) qu.getResultList();
	}
	
	public List<Resident> findByFirstnameANDSurname(String firstname, String surname){
		TypedQuery<Resident> query = entityManager.createQuery
				("select r from Resident r where r.firstname = :firstname and r.surname= :surname",Resident.class);
		query.setParameter("firstname", firstname);
		query.setParameter("surname", surname);
		return query.getResultList();
	}
	
}
