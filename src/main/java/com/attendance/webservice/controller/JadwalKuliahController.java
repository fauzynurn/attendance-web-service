package com.attendance.webservice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.service.JadwalKuliahService;

@RestController
public class JadwalKuliahController {
	@Autowired
	JadwalKuliahService jadwalService;
	
	@GetMapping("/getjadwal")
	public List getSchedule(@RequestBody HashMap<String, String> request) {
		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		return jadwalService.fetchJadwalKuliah(request.get("kdKelas"), hari);
	}
}
