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

import com.attendance.webservice.model.Ruangan;
import com.attendance.webservice.repository.RuanganRepository;

@RestController
public class RuanganController {
	@Autowired
	RuanganRepository ruanganRepository;
	
	@CrossOrigin
	@GetMapping("/getdaftarruangan")
	public List<Ruangan> getDaftarRuangan() {
        return ruanganRepository.findAll(Sort.by(Sort.Direction.ASC, "kdRuangan"));
	}
	
	@CrossOrigin
	@PostMapping("/ubahbeacon")
	public Map<String, String> ubahBeacon(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		Ruangan ruangan = ruanganRepository.findByKdRuangan(request.get("kdRuangan"));
		
		ruangan.setMacAddress(request.get("macAddress"));
		ruanganRepository.save(ruangan);
		
		map.put("status", "200");
		map.put("message", "Success");
        return map;
	}
	
	@CrossOrigin
	@PostMapping("/importruangan")
	public Map<String, String> importRuangan(@RequestBody Map<String, List<Map<String, String>>> request) {
		Map<String, String> map = new LinkedHashMap<>();
		List<Ruangan> listRuangan = new ArrayList<>();
		List<Map<String, String>> listRequest = request.get("listRuangan");
		
		for(Map<String, String> r : listRequest) {
			Ruangan ruangan = ruanganRepository.findByKdRuangan(r.get("kdRuangan"));
			if(ruangan == null) {
				Ruangan rgn = new Ruangan();
				rgn.setKdRuangan(r.get("kdRuangan"));
				listRuangan.add(rgn);
			}
		}

		try {
			if(!listRuangan.isEmpty()) {
				for(Ruangan item : listRuangan) {
					ruanganRepository.insertRuangan(item.getKdRuangan(), null);
				}
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
