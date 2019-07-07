package com.attendance.webservice.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Kelas;
import com.attendance.webservice.repository.KelasRepository;

@RestController
public class KelasController {
	@Autowired
	KelasRepository kelasRepository;
	
	@CrossOrigin
	@GetMapping("/getdaftarkelas")
	public List<Map<String, String>> getDaftarKelas() {
		List<Map<String, String>> maps = new ArrayList<>();
		
		List<Kelas> kelas = kelasRepository.findAll();
		for(Kelas item : kelas) {
			Map<String, String> map = new LinkedHashMap<>();
			
			map.put("kdKelas", item.getKdKelas());
			map.put("prodi", item.getProdi());
			map.put("dosenWali", item.getDosen1().getNamaDosen());
			map.put("ketuaProdi", item.getDosen2().getNamaDosen());
			maps.add(map);
		}
		return maps;
	}
}
