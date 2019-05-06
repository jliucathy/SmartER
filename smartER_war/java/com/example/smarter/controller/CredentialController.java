package com.example.smarter.controller;

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

import com.example.smarter.entity.Credential;
import com.example.smarter.service.CredentialService;

@RestController
public class CredentialController {
	@Autowired
	private CredentialService credentialservice;
	
	@PostMapping("/api/createCredential")
	public void createCredential(@Valid @RequestBody Credential credential) {
		credentialservice.createCredential(credential);
	}
	
	@GetMapping("/api/findByUsername/{username}")
	public List<Credential> findByUsername(@PathVariable(value = "username") String username) {
		return credentialservice.findByUsername(username);
	}
	
	@GetMapping("/api/findAllCredential")
	public List<Credential> findAllCredential(){
		return credentialservice.findAllCredential();
	}
	
	@GetMapping("/api/findByResid/{resid}")
	public List<Credential> findByResid(@PathVariable(value ="resid") int resid) {
		return credentialservice.findByResid(resid);
	}
	
	@PutMapping("/api/updateCredential")
	public void updateCredential(@Valid @RequestBody Credential credential) {
		credentialservice.updateCredential(credential);
	}
	
	@DeleteMapping("/api/removeCredential/{username}")
	public void removeCredential(@PathVariable(value = "username") String username) {
		credentialservice.removeCredential(username);
	}
}
