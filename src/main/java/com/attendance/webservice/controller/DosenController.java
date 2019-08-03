package com.attendance.webservice.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return dosenRepository.findAll(Sort.by(Sort.Direction.ASC, "kdDosen"));
	}
	
	@CrossOrigin
	@PostMapping("/resetdosen")
	public Map<String, String> resetDosen(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		Dosen dosen = dosenRepository.findByKdDosen(request.get("kdDosen"));
		
		dosen.setImeiDosen(null);
		dosenRepository.save(dosen);
		
		map.put("status", "200");
		map.put("message", "Success");
        return map;
	}
	
	@CrossOrigin
	@PostMapping("/importdosen")
	public Map<String, String> importDosen(@RequestBody Map<String, List<Map<String, String>>> request) {
		Map<String, String> map = new LinkedHashMap<>();
		List<Dosen> listDosen1 = new ArrayList<>();
		List<Dosen> listDosen2 = new ArrayList<>();
		List<Map<String, String>> listRequest = request.get("listDosen");
		
		for(Map<String, String> r : listRequest) {
			Dosen dosen = dosenRepository.findByKdDosen(r.get("kdDosen"));
			if(dosen == null) {
				Dosen dsn = new Dosen();
				dsn.setKdDosen(r.get("kdDosen"));
				dsn.setNamaDosen(r.get("namaDosen"));
				listDosen1.add(dsn);
			} else {
				dosen.setNamaDosen(r.get("namaDosen"));
				listDosen2.add(dosen);
			}				
		}

		try {
			if(!listDosen1.isEmpty()) {
				for(Dosen item : listDosen1) {
					dosenRepository.insertDosen(item.getKdDosen(), item.getNamaDosen(), null);	
				}
			}
				
			if(!listDosen2.isEmpty()) {
				dosenRepository.saveAll(listDosen2);	
			}
			
			map.put("status", "200");
			map.put("message", "Success");
		} catch(Exception e) {
			map.put("status", "404");
			map.put("message", "Import Fail");
		}
		return map;
	}
}
