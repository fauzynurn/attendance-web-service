package com.attendance.webservice.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Map<String, String> checkDosen(@RequestBody Map<String, String> request) {
		Map<String, String> map = new LinkedHashMap<>();
        Dosen dosen = dosenRepository.findByKdDosen(request.get("kdDosen"));
    	if(dosen == null) {
    		map.put("status", "404");
    		map.put("message", "User is not recognized");
    	} else {
    		if(dosen.getPasswordDosen().equals(request.get("password"))) {
    			if(dosen.getImeiDosen() != null) {
    				map.put("status", "200");
    				map.put("message", "User is active");
    			} else {
    				map.put("status", "200");
    				map.put("message", "User is not active");
    			}
    		} else {
    			map.put("status", "404");
        		map.put("message", "User is not recognized");
    		}
		}
        return map;
	}

	@PostMapping("/registerdosen")
	public Map<String, String> registerDosen(@RequestBody Map<String, String> request) {
		Map<String, String> map = new LinkedHashMap<>();
        Dosen dosen = dosenRepository.findByKdDosen(request.get("kdDosen"));
        dosen.setPubKeyDosen(request.get("publicKey"));
        dosen.setImeiDosen(request.get("imei"));
        dosenRepository.save(dosen);
        
        map.put("status","200");
        map.put("message", "Success");
        return map;
	}
}
