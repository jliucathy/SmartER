package com.example.smarter.controller;

import java.util.List;

import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.smarter.entity.Resident;
import com.example.smarter.service.ResidentService;

@RestController
public class ResidentController {
	@Autowired
//    @Qualifier("ResidentController")
	private ResidentService residentService;
	
	@PostMapping("/api/createResident")
	public void createResident(@Valid @RequestBody Resident resident) {
		residentService.createResident(resident);
	}
	
	@PutMapping("/api/changeResident")
	public void updateResident(@Valid @RequestBody Resident resident) {
        residentService.updateResident(resident);
    }
	
	@DeleteMapping("/api/removeResident/{id}")
	public void removeResident(@PathVariable(value = "id") int resid) {
		residentService.removeResident(resid);
	}
	
	 @GetMapping("/api/findResident/{id}")
	    public Resident viewUserById(@PathVariable(value = "id") int resid) {
	        return residentService.getResiddentById(resid);
	    }
	 
	 @GetMapping("/api/findAllResident")
	 public List<Resident> viewAllUser() {
	        return residentService.findAllResident();
	 }
	 
	 @GetMapping("/api/findByEnergyprovider/{energyprovider}")
	 public List<Resident> findByEnergyprovider(@PathVariable(value = "energyprovider") String energyProvider) {
	        return residentService.findByEnergyprovider(energyProvider);
	 }
	 
	 @GetMapping("/api/findByFirstnameANDSurname/{firstname}/{surname}")
	 public List<Resident> findByFirstnameANDSurname(@PathVariable(value = "firstname") String firstname, 
			 @PathVariable(value = "surname") String surname) {
	        return residentService.findByFirstnameANDSurname(firstname, surname);
	 }

}
