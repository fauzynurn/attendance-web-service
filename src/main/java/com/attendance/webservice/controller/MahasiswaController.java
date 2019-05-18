package com.attendance.webservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.service.MahasiswaService;

@RestController
public class MahasiswaController {
	@Autowired
	MahasiswaService mhsService;
	
	@PostMapping("/checkmhs")
	public Map<String, String> checkMhs(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
        Mahasiswa mhs = mhsService.findNim(request.get("nim"));
    	if(mhs == null) {
    		map.put("status", "404");
    		map.put("message", "User is not recognized");
    	} else {
    		if(mhs.getPwdMhs().equals(request.get("password"))) {
    			if(mhs.getImeiMhs() != null) {
    				if(mhs.getImeiMhs().equals(request.get("imei"))){
						map.put("status", "200");
						map.put("message", "Imei match");
					}else {
						map.put("status", "200");
						map.put("message", "User is active");
					}
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

//	@PostMapping("/handlevalidate")
//	public Map<String,String> testingPurposeOnly(@RequestBody HashMap<String,String> req){
//		HashMap<String, String> map = new HashMap<>();
//		if(req.get("nim").equals("161511049")){
//			map.put("status","200");
//			map.put("message", "User is not active");
//			return map;
//		}
//		map.put("status","200");
//		map.put("message", "User is active");
//		return map;
//	}

//	@PostMapping("/handleregister")
//	public Map<String,String> testingPurposeOnly1(@RequestBody HashMap<String,String> req){
//		HashMap<String, String> map = new HashMap<>();
//			map.put("status","200");
//			map.put("message", "3B");
//			return map;
//	}

	@PostMapping("/registermhs")
	public Map<String, String> registerMhs(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
        Mahasiswa mhs = mhsService.findNim(request.get("nim"));
        mhs.setPubKeyMhs(request.get("publicKey"));
        mhs.setImeiMhs(request.get("imei"));
        mhsService.saveMhs(mhs);
        
        map.put("status","200");
        map.put("message", mhs.getKelas().getKdKelas());
        return map;
	}
}
