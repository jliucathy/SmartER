package com.example.smarter.service;

import java.util.List;

import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.smarter.dao.CredentialDao;
import com.example.smarter.entity.Credential;

@Service("CredentialService")
public class CredentialService {
	@Autowired
	private CredentialDao credentialDao;
	
	@Transactional
	public List<Credential> findByUsername(String username) {
		return credentialDao.findByUsername(username);
	}
	
	@Transactional
	public List<Credential> findByResid(int resid) {
		return credentialDao.findByResid(resid);
	}
	
	@Transactional
	public List<Credential> findAllCredential(){
		return credentialDao.findAllCredential();
	}
	
	@Transactional
	public void createCredential(Credential credential) {
		credentialDao.createCredential(credential);
	}
	
	@Transactional
    public void updateCredential(Credential credential) {
		credentialDao.updateCredential(credential);
	}
    
    @Transactional
	public void removeCredential(String username) {
    	credentialDao.removeCredential(username);
    }
	
	
}
