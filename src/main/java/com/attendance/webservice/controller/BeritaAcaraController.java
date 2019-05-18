package com.attendance.webservice.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.service.BeritaAcaraService;;

@RestController
public class BeritaAcaraController {
	@Autowired
	BeritaAcaraService beritaService;
	
	@PostMapping("/aktifabsensi")
	public BeritaAcara aktifAbsensi(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
		JadwalKuliah jdwl = new JadwalKuliah();
		jdwl.setIdJadwal(48);
		Date now = new Date(System.currentTimeMillis());
		List idJadwal = beritaService.fetchIdJadwalDataInnerJoin(request.get("kdMatkul"), request.get("kdKelas"), request.get("hari"));

//		for(int i = 0; i < idJadwal.size(); i++) {
		BeritaAcara brt = new BeritaAcara(now, null, null, false, false, false, false);
//			brt.setTglAbsensi(now);
//			brt.setJdwlKuliah(jdwl);
		
			beritaService.saveBerita(brt);
//		}
        
        map.put("status","200");
        map.put("message", "Success");
        return brt;
	}
	
	@GetMapping("/getidjadwal")
	public int getIdJadwal(@RequestBody HashMap<String, String> request) {
		List idJadwal = beritaService.fetchIdJadwalDataInnerJoin(request.get("kdMatkul"), request.get("kdKelas"), request.get("hari"));
		return (int) idJadwal.get(0);
	}
}
