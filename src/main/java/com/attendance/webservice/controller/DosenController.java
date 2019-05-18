package com.attendance.webservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Dosen;
import com.attendance.webservice.service.DosenService;

@RestController
public class DosenController {
	@Autowired
	DosenService dsnService;
	
	@PostMapping("/checkdosen")
	public Map<String, String> checkDosen(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
        Dosen dsn = dsnService.findKdDosen(request.get("kdDosen"));
    	if(dsn == null) {
    		map.put("status", "404");
    		map.put("message", "User is not recognized");
    	} else {
    		if(dsn.getPwdDosen().equals(request.get("password"))) {
    			if(dsn.getImeiDosen() != null) {
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
	public Map<String, String> registerDosen(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
        Dosen dsn = dsnService.findKdDosen(request.get("kdDosen"));
        dsn.setPubKeyDosen(request.get("publicKey"));
        dsn.setImeiDosen(request.get("imei"));
        dsnService.saveDsn(dsn);
        
        map.put("status","200");
        map.put("message", "Success");
        return map;
	}
}
