package com.attendance.webservice.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Dosen;
import com.attendance.webservice.repository.DosenRepository;

@RestController
public class DosenController {
	@Autowired
	DosenRepository dosenRepository;
	
	@PostMapping("/checkdosen")
	public Map checkDosen(@RequestBody Map<String, String> request) {
		Map map = new LinkedHashMap<>();
		Map<String, String> data = new LinkedHashMap<>();
		
        Dosen dosen = dosenRepository.findByKdDosen(request.get("kdDosen"));
    	if(dosen == null) {
    		map.put("status", "404");
    		map.put("message", "User is not recognized");
    	} else {
    		if(dosen.getImeiDosen() != null) {
    			if(dosen.getImeiDosen().equals(request.get("imei"))) {
    				data.put("nama", dosen.getNamaDosen());
    				
					map.put("status", "200");
					map.put("message", "Imei match");
					map.put("data", data);
				} else {
					map.put("status", "200");
					map.put("message", "User is active");
				}
    		} else {
    			map.put("status", "200");
    			map.put("message", "User is not active");
    		}
		}
        return map;
	}

	@PostMapping("/registerdosen")
	public Map registerDosen(@RequestBody Map<String, String> request) {
		Map map = new LinkedHashMap<>();
		Map<String, String> data = new LinkedHashMap<>();
		
        Dosen dosen = dosenRepository.findByKdDosen(request.get("kdDosen"));
        dosen.setImeiDosen(request.get("imei"));
        dosenRepository.save(dosen);
        
		data.put("nama", dosen.getNamaDosen());
		
		map.put("status", "200");
		map.put("message", "Success");
		map.put("data", data);
        return map;
	}
	
	@CrossOrigin
	@GetMapping("/getdaftardosen")
	public List<Dosen> getDaftarDosen() {
        return dosenRepository.findAll();
	}
	
	@PostMapping("/buatdosen")
	public Map<String, String> buatDosen(@RequestBody Map<String, String> request) {
		Map<String, String> map = new LinkedHashMap<>();
		Dosen dosen = new Dosen();
		
		if(dosenRepository.findByKdDosen(request.get("kdDosen")) == null) {
			dosen.setKdDosen(request.get("kdDosen"));
			dosen.setNamaDosen(request.get("namaDosen"));
			dosen.setPasswordDosen(request.get("passwordDosen"));
			dosenRepository.save(dosen);
			
			map.put("status", "200");
			map.put("message", "Success");
		} else {
			map.put("status", "404");
			map.put("message", "Failed");
		}
        return map;
	}
}
