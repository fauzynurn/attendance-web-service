package com.attendance.webservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.repository.AbsensiRepository;

@RestController
public class AbsensiController {
	@Autowired
	AbsensiRepository absensiRepository;
	
	@GetMapping("/getmatkulkehadiran")
	public List<Map> getMatkulKehadiran(@RequestBody HashMap<String, String> request) {
		return absensiRepository.fetchMatkulKehadiran(request.get("nim"));
	}
	
	@GetMapping("/getallkehadiran")
	public List<Map> getAllKehadiran(@RequestBody HashMap<String, String> request) {
		return absensiRepository.fetchAllKehadiran(request.get("nim"));
	}
}
