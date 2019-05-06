package com.example.smarter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
 
import org.springframework.stereotype.Repository;

import com.example.smarter.entity.Credential;

@Repository("CredentialDao")
public class CredentialDao {
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Credential> findByUsername(String username){
		Query query=entityManager.createNamedQuery("Credential.findByUsername");        
        query.setParameter("username",username);
        return query.getResultList(); 
	}
	
	public List<Credential> findByResid(int resid){
		TypedQuery<Credential> qu = entityManager.createQuery
		        ("SELECT c FROM Credential c WHERE c.resid.resid = :resid", Credential.class);
		        qu.setParameter("resid", resid); 
		        return qu.getResultList();
	}
	
	public List<Credential> findAllCredential(){
		Query query=entityManager.createNamedQuery("Credential.findAll");
		return query.getResultList();
	}
	
	public void createCredential(Credential credential) {
		entityManager.persist(credential);
	}
	
	public Credential findCreByUsername(String username){
		Query query=entityManager.createNamedQuery("Credential.findByUsername");        
        query.setParameter("username",username);
        return (Credential) query.getResultList().get(0); 
	}
	
	public void updateCredential(Credential credential)
	{
		Credential credentialToUpdate = findCreByUsername(credential.getUsername());
		credentialToUpdate.setUsername(credential.getUsername());
		credentialToUpdate.setPassword(credential.getPassword());
		credentialToUpdate.setRegistrationdate(credential.getRegistrationdate());
		credentialToUpdate.setResid(credential.getResid());
		entityManager.flush();
	}
	
	public void removeCredential(String username) {
		entityManager.remove(findByUsername(username));
	}

}
