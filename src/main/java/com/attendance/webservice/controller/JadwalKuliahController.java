package com.attendance.webservice.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.service.JadwalKuliahService;

@RestController
public class JadwalKuliahController {
	@Autowired
	JadwalKuliahService jdwlKuliahService;
	
	@GetMapping("/getjadwal")
	public List getJdwlKelas(@RequestBody HashMap<String, String> request) {
		return jdwlKuliahService.fetchJdwlKuliahDataInnerJoin(request.get("kdKelas"));
	}
}
