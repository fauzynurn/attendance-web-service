package com.attendance.webservice.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping("/checkdsn")
	public Map<String, String> checkDsn(@RequestBody HashMap<String, String> request) {
		Map<String, String> map = new LinkedHashMap<>();
        Dosen dosen = dosenRepository.findByKdDosen(request.get("kddsn"));
    	if(dosen == null) {
    		map.put("status", "404");
    		map.put("message", "User is not recognized");
    	} else {
    		if(dosen.getImeiDosen() != null) {
    			if(dosen.getImeiDosen().equals(request.get("imei"))) {
					map.put("status", "200");
					map.put("message", "Imei match");
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

	@PostMapping("/registerdsn")
	public Map<String, String> registerDsn(@RequestBody HashMap<String, String> request) {
		Map<String, String> map = new LinkedHashMap<>();
        Dosen dosen = dosenRepository.findByKdDosen(request.get("kddsn"));
        dosen.setImeiDosen(request.get("imei"));
        dosenRepository.save(dosen);
        
        map.put("status","200");
        map.put("message", "Success");
        return map;
	}
	
	@GetMapping("/getdsn")
	public List<Dosen> getDsn() {
        return dosenRepository.findAll();
	}
}
